package com.roomautomation.service;

import java.security.SecureRandom;
import java.util.Random;


public class PasswordGeneration {

	  private static final Random RANDOM = new SecureRandom();
	  public static final int PASSWORD_LENGTH = 6;
	  
	  public static String generateRandomPassword()
	  {
	      String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

	      String pw = "";
	      for (int i=0; i<PASSWORD_LENGTH; i++)
	      {
	          int index = (int)(RANDOM.nextDouble()*letters.length());
	          pw += letters.substring(index, index+1);
	          
	      }
	      System.out.println("in generate pass "+ pw);
	      return pw;
	  }
	}


