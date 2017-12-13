package com.obs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.obs.databean.Account;
import com.obs.databean.BillPayee;
import com.obs.databean.BillPayeeType;
import com.obs.databean.Customer;
import com.obs.databean.RecurBillPay;
import com.obs.databean.Transaction;
import com.obs.databean.TransactionType;
import com.obs.formbean.BillPayForm;
import com.obs.repository.AccountRepository;
import com.obs.repository.BillPayeeRepository;
import com.obs.repository.BillPayeeTypeRepository;
import com.obs.repository.RecurBillPayRepository;
import com.obs.repository.TransactionRepository;
import com.obs.repository.TransactionTypeRepository;

@Controller
public class BillPayController {
    @Autowired
    AccountRepository ar;
    
    @Autowired
    BillPayeeRepository bpr;
    
    @Autowired
    BillPayeeTypeRepository bptr;

    @Autowired
    TransactionTypeRepository ttr;

    @Autowired
    TransactionRepository tr;
    
    @Autowired
    RecurBillPayRepository rbpr;
    
    @GetMapping("/billpay")
    public String BillPayPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        
        List<Account> accounts = new ArrayList<>();
        List<RecurBillPay> recurBillPay = new ArrayList<>();
        Iterable<BillPayee> billPayees = bpr.findAll();
        
//        Iterable<BillPayeeType> billPayeeTypes = new Iterable<BillPayeeType>() {
//			
//			@Override
//			public Iterator<BillPayeeType> iterator() {
//				int index = 0;
//				boolean hasNext() {
//					return (index < billPayeeTypes.)
//				}
//			}
//		};
        
        accounts = ar.findByCustomer_CustomerId(customer.getCustomerId());
        recurBillPay = rbpr.findByCustomer_CustomerId(customer.getCustomerId());

        List<Account> activeAccounts = new ArrayList<>();
        
        for (Account account: accounts) {
            if ("active".equals(account.getStatus())) {
                activeAccounts.add(account);
            }
        }
        
        model.addAttribute("customer", customer);
        model.addAttribute("accounts", activeAccounts);
        model.addAttribute("billPayForm", new BillPayForm());
        model.addAttribute("recurBillPay", recurBillPay);
        model.addAttribute("billPayees", billPayees);
        
        return "billPayment";   
    }
    
    @PostMapping("/confirmbillpay")
    public String ConfirmBillPay(@ModelAttribute BillPayForm billPayForm, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("customer");
        
        long fromId = Long.parseLong(billPayForm.getFromAccountId());
        Account from = ar.findOne(fromId);
        long toPayeeId = Long.parseLong(billPayForm.getToAccountName());
        BillPayee toPayee = bpr.findOne(toPayeeId);
        
        model.addAttribute("customer", c);
        model.addAttribute("from", from);
        model.addAttribute("toPayee", toPayee);
        model.addAttribute("confirmBillPay", new BillPayForm());
        
        return "confirm-bill-payment";
    }
    
    @PostMapping("/processbillpay")
    public String BillPay(@ModelAttribute BillPayForm confirmBillPay, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("customer");
        
        long fromId = Long.parseLong(confirmBillPay.getFromAccountId());
        Account from = ar.findOne(fromId);
        long toPayeeId = Long.parseLong(confirmBillPay.getToAccountName());
        BillPayee toPayee = bpr.findOne(toPayeeId);
        
        model.addAttribute("from", from);
        model.addAttribute("toPayee", toPayee);
        model.addAttribute("customer", c);
        model.addAttribute("confirmBillPay", confirmBillPay);
        
        double amount = Double.parseDouble(confirmBillPay.getAmount());
        String frequency = confirmBillPay.getFrequency();
        
        if (from.getBalance() >= amount) {
            if (frequency == null || frequency.equals("One time, immediately")) {
                from.setBalance(from.getBalance() - amount);
                Date d = new Date();
                String description = "Paid Bill " + amount + " from " + from.getAccountNumber() + " to "
                        + toPayee.getBillPayeeName();
                TransactionType t = ttr.findByTransactionTypeName("Bill Pay").get(0);
                Transaction transaction = new Transaction(d, -amount, from.getBalance(), t, from, null, toPayee, description, "Clear");
                tr.save(transaction);
            } else {
                Customer customer = from.getCustomer();
                String toAccountName = toPayee.getBillPayeeName();
                RecurBillPay recurBillPay = new RecurBillPay(customer, toPayee, fromId, from.getAccountNumber(),
                        from.getAccountType().toString(), toAccountName, amount, frequency);
                rbpr.save(recurBillPay);
            }
        
        return "success-bill-payment";
        } else {
        model.addAttribute("error", "Not enough money");
        return "redirect:billpay";
        }
    }
    
}
