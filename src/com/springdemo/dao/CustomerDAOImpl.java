package com.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = 
				sessionFactory.getCurrentSession();
		
		// create a query, get the customers sorted by the last name
		Query<Customer> theQuery = 
				currentSession.createQuery(
						"from Customer order by lastName", 
						Customer.class);
		
		// execute the query and get result list 
		List<Customer> customers = theQuery.getResultList();
		
		// return the result
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get the current hibernate session
		Session currentSession = 
				sessionFactory.getCurrentSession();
		
		// save the customer
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = 
				sessionFactory.getCurrentSession();
		
		// retrieve the customer from database using primary key
		Customer theCustomer = 
				currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = 
				sessionFactory.getCurrentSession();
		
		// retrieve the customer from database using primary key
		Customer theCustomer = 
				currentSession.get(Customer.class, theId);
		
		// delete the customer
		currentSession.delete(theCustomer);
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query<Customer> theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery = 
            		currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
            									Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        } else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
	}

}