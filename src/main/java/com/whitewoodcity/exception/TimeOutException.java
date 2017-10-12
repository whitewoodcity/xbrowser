package com.whitewoodcity.exception;

public class TimeOutException extends Exception{

    public TimeOutException(long maxExecutionTime){
        super("Execution timeout, max execution time is: "+maxExecutionTime);
    }
}
