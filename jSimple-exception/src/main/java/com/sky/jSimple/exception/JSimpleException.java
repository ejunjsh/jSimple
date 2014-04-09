package com.sky.jSimple.exception;

public class JSimpleException extends Exception{
 
	public JSimpleException(Throwable e)
    {
	
       super(e);
    }
	
	public JSimpleException(String message)
	{
		super(message);
	}

}
