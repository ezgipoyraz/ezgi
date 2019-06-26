/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findnumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ezgi
 */
public class SecretKeyComputer {
 
  public static int randomNum=0;// Tahmin edilecek sayı
  public static int userGuessNum=0; //Kullanıcının tahmin ettiği sayı
  public static  boolean createNum = true; // Tahmin edilecek sayı
  public static  boolean userGuessGame = true; //Kullanıcı tahmin kontrolü
  public static  boolean softwareGuessGame = true;//Yazılım tahmin kontrolü
    /**
     * @param args the command line arguments
     */
    public static void SecretKeyComputer() {

               randomNum=createUniqueFourDigit();
               Scanner sc = new Scanner(System.in);

                   while (userGuessGame) {
                   try{
                   System.out.println("Lütfen rakamları farklı 4 basamaklı sayı giriniz:");
                   userGuessNum = sc.nextInt();
                   if(userGuessNum>1000 && userGuessNum<9999){// Kullanıcı tahmini değerinin 4 basamaklı bir sayı olduğunu kontrol eder
                   if(controlUniqueFourDigit(userGuessNum)==1){
                     int[] userResult=new int[2];
                     userResult=controlUserGuessFourDigit(userGuessNum, randomNum); //Gizli sayı ve kullanıcı tahmin numarasının karşılaştırıltırılır
                      if(userResult.length>0){
                      int posresult=userResult[0];
                      int negresult=userResult[1];
                      if(posresult==4){ //Pozitif değer 4 ise gizli sayıdır
                           userGuessGame=false;
                           System.out.println("Tebrikler. Kazandınız!");
                      }
                        System.out.println("Pozitif değer: "+posresult+" Negatif değer: "+negresult);
                      }
                     }
                     else
                        System.out.println("Rakamları farklı 4 basamaklı sayı değil!!");  
                   }else{
                   System.out.println("4 basamaklı sayı giriş yapılmalıdır!!");
                   }
                   } 
                    catch (InputMismatchException e) 
                     {
                                System.out.println("Error: Please enter integer type number!");
                                userGuessGame=false; 
                     }
                      catch (Exception e) 
                       {
                       System.out.println("Error: "+e.getMessage());
                        userGuessGame=false; 
                        }
                  }

 }
    
    // 4 basamaklı rakamlardan farklı sayılar oluşturma
    public static int createUniqueFourDigit()
  {
    int number =0;
    boolean createNum=true;
    while(createNum){
   
    List<Integer> numbers = new ArrayList<>();
    for(int i = 0; i < 10; i++){
      
        numbers.add(i);
    }

    Collections.shuffle(numbers);

    String result = "";
    for(int i = 0; i < 4; i++){
        result += numbers.get(i).toString();
    }
    System.out.println(result);
    number = Integer.parseInt(result);
    
      if(number>1000 && number<9999)
       createNum=false;
     else
          continue;
    }
    
  //  System.out.println("Hidden Number: "+String.valueOf(number));
      return number;
  }
    //Girilen değerin 4 basamaklı bir sayı olup olmadığını kontrol eder
    public static  int controlUniqueFourDigit(int number)
   {
   int temp, i, j;
 
   for (i = 0; i < 10; i++) {
      j = 0;
      temp = number;
      while (temp > 0) {
         if (temp % 10 == i)
            j++;
         if (j > 1)
            return 0;
         temp /= 10;
      }
   }
   return 1;
}
    
    // Bir tamsayı değerini bir diziye dönüştürme
 public static int[] arrayFormat(int number){
   int[] numArray = new int[4];
        for (int i = 0; i < 4; i++) {
                    int num = 0;
                    num = number % 10;
                    numArray[i] = num;
                    number = number / 10;
               }
 return numArray;
 }
 
 // Bir diziyi bir tamsayı değerine dönüştürme
  public static int intFormat(int[] numberArray){
    int numArray = 0;
    String number="";
    for(int j=3;j>=0;j--){
    number+=numberArray[j];
    }
    numArray=Integer.parseInt(number);
    return numArray;
 }
   
//Gizli anahtarı kullanıcının tahminiyle karşılaştırarak pozitif ve negatif sonuç değerlerini hesaplayan fonksiyon
 public static int[] controlUserGuessFourDigit(int usrNum,int rndNum)
   {
       int [] resultArray=new int[2];
       int positiveCount = 0;
       int negativeCount = 0;
       int[] usrNumArray=arrayFormat(usrNum);//kullanıcı tahmin numarası diziye dönüştürmek
       int[] rndNumArray=arrayFormat(rndNum);// gizli numarayı diziye dönüştürmek
              for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        
                         if (rndNumArray[i] == usrNumArray[j] && i == j)// Gizli sayının sayı ve hane değeri tahmin edilen sayı ile aynıysa, pozitif değer artar.
                              positiveCount++;

                         if (rndNumArray[i] == usrNumArray[j] && i != j) //Gizli sayıya sahip tahmini bir sayı vardır, ancak basamak yanlışsa negatif değer azaltılır.
                              negativeCount--;
                    }
              }
              resultArray[0]=positiveCount;
              resultArray[1]=negativeCount;
              System.out.println(positiveCount + " " + negativeCount);
              return resultArray;
}                          

    
   
}
