package com.rahulp.test;

import javax.mail.Session;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.rahulp.test.model.Employee;
import com.rahulp.test.reotrfit.EmployeeApi;
import com.rahulp.test.reotrfit.RetrofitService;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    private String val;


    //Class Constructor
    public SendMail(Context context, String email, String subject, String message, String val){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.val = val;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        Log.v("msg","sending mail...");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Showing a success message

        Log.v("msg","mail sent");

    }

    @Override
    protected Void doInBackground(Void... params) {
        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);

        Employee employee = new Employee();
        employee.setName(Build.MODEL);
        employee.setBranch(message);
        employee.setLocation(DateTimeHelper.getCurrentDay());
        employee.setSalary(val);

        employeeApi.save(employee)
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Logger.getLogger(testService.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        Log.v("msg","onFailure");
                    }
                });

        return null;
    }
}
