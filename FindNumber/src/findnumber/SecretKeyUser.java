/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package findnumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ezgi
 */
public class SecretKeyUser {
   private static char[] _ValidDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; // 4 basamaklı sayı için kullanılan rakamlar
       private static List<String> possibleTokens; // Rakamları farklı 1000-9999 arasındaki tüm  4 basamaklı sayılar
       private static String _LastMove;
       public static int [][] ctable=new int[10000][6];// Tahmin edilen sayı  ve sonucların tutulduğu table
       public static int softwareGuessNum=0; // Tahmin edilen 4 basamklı sayı
       public static int row=0; // table satır kontrolü
       public static int tokenIndex=0; // tahmin edilen sayının index bilgisi
       private static int changedValue=-1;
       private static int token=0; // Doğru 4 rakam bulunduğunda satır kontrolu sağlar 
       private static List<String> finderNumber=new ArrayList<String>(); //tahminde pozitif sayı üreten rakamların listesi
       private static int finderNumberSize=0; //tahminde pozitif sayı üreten rakamların size
      
    /**
     * @param args the command line arguments
     */
    public static void SecretKeyUser(){
 
       boolean softwareGuessGame = true;
 
       System.out.println("Yazılım tahmin oyunu başladı...");  
       StartGuess();
         while (softwareGuessGame) {
          try{
          CreateComputerGuessNumber();//İlk Tahmin Oluşturulur
          UserAnswer(); // Kullanıcı cevabı alınır
          softwareGuessGame=PrunePossibleMovesForTheAnswer();// Tahmin Geliştirilen Metot
          row++;

         }
          catch (Exception e) {
           System.out.println("Error: "+e.getMessage());
            softwareGuessGame=false; 
            }
          }
        }
   
 
    // Bir diziyi 4 basamaklı sayıya döndürme
public static int[] numberToArray(int number)
{
	int[] res=new int[5];
	res[0]=(int)Math.floor((number%10000)/1000);
	res[1]=(int)Math.floor((number%1000)/100);
	res[2]=(int)Math.floor((number%100)/10);
	res[3]=(int)Math.floor(number%10);
	return(res);
}

// Kullanıcı cevaplarını alarak  tahmin tablosu yaratılır
 private static void UserAnswer(){
    Scanner sc = new Scanner(System.in);
    int positive=0; int negative=0;
    int[]cguess=numberToArray(softwareGuessNum);
    ctable[row][0]=cguess[1];
    ctable[row][1]=cguess[2];
    ctable[row][2]=cguess[3];
    ctable[row][3]=cguess[4];
    System.out.println("Tahmin edilen sayı:"+softwareGuessNum);
    System.out.println("Lütfen positive sonuç değerini giriniz..");
    positive=sc.nextInt();
    System.out.println("Lütfen negative sonuç değerini giriniz..");
    negative=sc.nextInt();
    ctable[row][4]=positive;
    ctable[row][5]=negative;
 }
    
 // Yazılım Tahmin edilecek sayıyı random oluşturur
    private static void CreateComputerGuessNumber(){
    boolean createNum = true;
      while(createNum){
      String gus=MakeMove();
      softwareGuessNum=Integer.parseInt(gus);
     if(softwareGuessNum>1000 && softwareGuessNum<9999)
     {
       System.out.println("4-Digit Number with Different Figures");
       createNum=false;
     }
     else{
        System.out.println("4-Digit Number Not Different Figures");
         gus=MakeMove();
        softwareGuessNum=Integer.parseInt(gus);
     } 
    }
      possibleTokens.remove(tokenIndex);
   }
    
    
    // Tahmin Geliştirilen Metot
    public static boolean PrunePossibleMovesForTheAnswer()
    {
     boolean ans=true;
     while(ans){
     if(ctable[row][4]==4)// tahmin edilecek sayı bulundu
     {
           ans=false;
           System.out.println("Tebrikler. Kazandınız!");
      }
    else if(ctable[row][4]!=0 || ctable[row][5]!=0) //Tahmin edilecek sayı içerisinde eşleşenleri bulma
      {
      int posandnegtotal=ctable[row][4]+Math.abs(ctable[row][5]);
       if(posandnegtotal==4) // tahmin edilecek sayıyı oluşturan rakamların tamamını içeriyorsa tahmin listesini güncelleyerek sonucu bulur 
       {
           findByChangedNumbers(softwareGuessNum);// yeni tahmin listesini oluşturur
           row++;
           UserAnswer();
           token++;
       }
       else{
          if(row==0)
           {
          softwareGuessNum=increment(numberToArray(softwareGuessNum));
          if(softwareGuessNum!=-1){
          row++;
          UserAnswer();
          }
          else
          {
            CreateComputerGuessNumber();
             row++;
            UserAnswer(); 
           }
           }
           else if(finderNumberSize==4) // tahmin edilecek sayıyı oluşturan rakamların tamamını içeriyorsa tahmin listesini güncelleyerek sonucu bulur 
           {
                String guessNumber="";
               for(int i=0;i<finderNumber.size();i++)
                   guessNumber+=finderNumber.get(i);
           findByChangedNumbers(Integer.parseInt(guessNumber));
           row++;
           token++;
           UserAnswer();
           
             }
        
         else if(ctable[row][4]==ctable[row-1][4] && ctable[row][5]==ctable[row-1][5]) //Tahminde değişiklik yaratmıyorsa listeden yeni sayı seç
          {
            changedValue=-1;
            CreateComputerGuessNumber();
            row++;
            UserAnswer(); 
           }
         else if(ctable[row][4]>ctable[row-1][4] || Math.abs(ctable[row][5])>Math.abs(ctable[row-1][5])) //Tahmin artışı sağlayan rakamları bulma
          {
            if(changedValue!=-1){
            PossibleTokenListFinderRemoveNumberAll(String.valueOf(changedValue)); // değişen sayıyı al tahmin kümesinde o rakamı içeren  4 basamaklı sayılar kalsın
             finderNumberLists(String.valueOf(changedValue));
            }
            
           softwareGuessNum=increment(numberToArray(softwareGuessNum));
           
           if(softwareGuessNum!=-1){
          row++;
          UserAnswer();
          }
          else
          {
            changedValue=-1;
            CreateComputerGuessNumber();
             row++;
            UserAnswer(); 
           }
           }
         else if(ctable[row][4]<ctable[row-1][4] || Math.abs(ctable[row][5])< Math.abs(ctable[row-1][5])) //Tahminde doğruluk azalışına neden olan rakamları bulma
          {
            if(changedValue!=-1){
            PossibleTokenListRemoveNumber(String.valueOf(changedValue)); // değişen sayıyı al tahmin kümesinde o rakamı içeren  4 basamaklı sayıları çıkar
            }
           softwareGuessNum=increment(numberToArray(softwareGuessNum));
           if(softwareGuessNum!=-1){
          row++;
          UserAnswer();
          }
          else
          {
            changedValue=-1;
            CreateComputerGuessNumber();
             row++;
            UserAnswer(); 
           }
           }
           }


      }
         else if(ctable[row][4]==0 && ctable[row][5]==0){ // Tahmin, tahmin edilecek hiç bir sayı ile uyumluluk göstermiyorsa  o rakamları kümeden çıkar yeni tahmin oluştur
             String number=String.valueOf(softwareGuessNum);
             for(int i=0;i<number.length();i++)
             {
             PossibleTokenListRemoveNumber(String.valueOf(number.charAt(i)));
             }
            changedValue=-1;
            CreateComputerGuessNumber();
            row++;
            UserAnswer();
          }
         }
     return ans;
      }
    
    // Pozitif ya da Negatif Sayıyı arttıran sayılar kümesini oluşturur
    public static int finderNumberLists(String number){
        int count=0;
    if(finderNumberSize>0){
    for(int i=0;i<finderNumber.size();i++)
    {
        if(finderNumber.get(i).contains(number))
        { count++;}
    }
    if(count==0)
    {finderNumber.add(number); }
    }else{
        finderNumber.add(number);
    }
    finderNumberSize=finderNumber.size();
        return finderNumberSize;
    }
    
    /* Tahmin edilecek 4 basamaklı sayını tüm rakamları bulunduğu için 4 sayı 
     için olabilecek tüm olasılıkları içeren küme oluşturularak yeni tahminler bu kümeden seçilir. */
    public static int findByChangedNumbers(int iguess){
        int value=0;
        if(token==0){
        if(possibleTokens.size()>24){
            _ValidDigits=String.valueOf(iguess).toCharArray();
            possibleTokens =GetAllFindPossibleTokens();
            if(String.valueOf(iguess).contains("0"))
            {PossibleTokensUpdate();}
            PossibleTokenListRemoveNumberAll(String.valueOf(softwareGuessNum));
        }
        }
        for (int i=0;i<possibleTokens.size();i++){
            value=Integer.parseInt(possibleTokens.get(i));
            softwareGuessNum=value;
            possibleTokens.remove(i);
            return value;
                }
      return -1;
    }
    
    // Pozitif ya da negatif artış sağlayacak rakamları deneyebilmek için belirli bir rakam attırılarak güçlü tahmin yaratmamızı sağlamaktadır.
    public static int increment(int[] iguess)
    {

        try{
        String result="";
	int [] guess=iguess;
        int temp=guess[3];
        temp++;
	if (temp<10) {
            if(temp<=9){
            while(temp<=9)
               {
               if(guess[2]!=temp && guess[1]!=temp && guess[0]!=temp)
               break;
               else
                   temp++;
            }
	   }
 
            }

          if(temp==10){
                int sayi=0;
               if(guess[2]!=0 && guess[1]!=0 && guess[0]!=0){
		temp=0;
               }else{
               while(sayi<9)
               {
               sayi++;
               if(guess[2]!=sayi && guess[1]!=sayi && guess[0]!=sayi){
		temp=sayi;
               break;
               }
               }
               }
                   }
  
        if(guess[3]== temp)
            return -1;
        else{
      guess[3]=temp;
      changedValue=temp;
      result=(String.valueOf(guess[0])+String.valueOf(guess[1]) +String.valueOf(guess[2])+ String.valueOf(guess[3]));
      PossibleTokenListRemoveNumberAll(result);
      return Integer.parseInt(result);  
        
        } 
        }catch(Exception ex){
       changedValue=-1;
        return -1;
        }
    }
    
 
    // Tahmin listesinden azalışa sebep olan 4 basamaklı sayı çıkarılır
      public static void PossibleTokenListRemoveNumberAll(String number){
    
        for(int i=0;i<possibleTokens.size();i++)
        {
          if(possibleTokens.get(i).contains(number))
          { possibleTokens.remove(i);}
        }
    } 
        // Tahmin listesinden azalışa sebep olan rakamlar çıkarılır.
    public static void PossibleTokenListRemoveNumber(String number){
    
        for(int i=0;i<possibleTokens.size();i++)
        {
         String num=possibleTokens.get(i);
         for(int j=0;j<num.length();j++){
         if(num.charAt(j)== number.charAt(0)){
             { possibleTokens.remove(i);}
        }
        }
        }
    }  
    
    // Tahmin listesinden atışa sebep olan rakamları içermeyen sayılar çıkarılır
        public static void PossibleTokenListFinderRemoveNumberAll(String number){
    
        for(int i=0;i<possibleTokens.size();i++)
        {
        if(!possibleTokens.get(i).contains(number))
        { possibleTokens.remove(i);}
        
        }
    } 
        
          // Tahmin listesinden atışa sebep olan rakamları içermeyen sayılar çıkarılır
      public static void PossibleTokenListFinderRemoveNumber(String number){
    
        for(int i=0;i<possibleTokens.size();i++)
        {
         String num=possibleTokens.get(i);
         int count=0;
         for(int j=0;j<num.length();j++){
         if(num.charAt(j)== number.charAt(0)){
             { count++;}
          if(count==0)
          {possibleTokens.remove(i);}
        }
        }
    } }
      
      // 4 basamaklı rakamları farklı tüm sayıları içeren küme yaratılır
    private static List<String> GetAllPossibleTokens()
    {
            List<String> tokens = new ArrayList<>();
            for (int d1 = 1; d1 < _ValidDigits.length; d1++)
                for (int d2 = 0; d2 < _ValidDigits.length; d2++)
                    for (int d3 = 0; d3 < _ValidDigits.length; d3++)
                        for (int d4 = 0; d4 < _ValidDigits.length; d4++)
                        {
                            if (d1 != d2 && d1 != d3 && d1 != d4
                                   && d2 != d3 && d2 != d4
                                    && d3 != d4)
                            {
 
                                tokens.add((String.valueOf(_ValidDigits[d1]) +String.valueOf( _ValidDigits[d2]) +String.valueOf( _ValidDigits[d3])+ String.valueOf(_ValidDigits[d4])));

                                }
                        }
            return tokens;
        }
    
    
      // Doğruluğu bulanan 4 rakam için 4 basamaklı rakamları farklı tüm sayıları içeren küme yaratılır
       private static List<String> GetAllFindPossibleTokens()
      {
            List<String> tokens = new ArrayList<>();
            for (int d1 = 0; d1 < _ValidDigits.length; d1++)
                for (int d2 = 0; d2 < _ValidDigits.length; d2++)
                    for (int d3 = 0; d3 < _ValidDigits.length; d3++)
                        for (int d4 = 0; d4 < _ValidDigits.length; d4++)
                        {
                            if (d1 != d2 && d1 != d3 && d1 != d4
                                   && d2 != d3 && d2 != d4
                                    && d3 != d4)
                            {
 
                                tokens.add((String.valueOf(_ValidDigits[d1]) +String.valueOf( _ValidDigits[d2]) +String.valueOf( _ValidDigits[d3])+ String.valueOf(_ValidDigits[d4])));

                                }
                        }
            return tokens;
        }
       
       // Küme de 0 ile başlayan 4 basamaklı sayılar çıkarılır.
       private static void PossibleTokensUpdate(){
        for(int i=0;i<possibleTokens.size();i++)
        {
        if(possibleTokens.get(i).charAt(0)=='0')
        {
            possibleTokens.remove(i);
        }
        }
       }
       
       
       // Tahmin kümesinden random sayı seçilir.
    public  static String MakeMove()
    {
            tokenIndex=new Random(System.currentTimeMillis()).nextInt(possibleTokens.size());
            _LastMove =  possibleTokens.get(tokenIndex);
            System.out.println("_LastMove"+_LastMove);
            return _LastMove;
        }
  //  Tahmin başladığında tüm olasıklar kümesini oluşturur.
    public static void StartGuess()
     {
            possibleTokens = GetAllPossibleTokens();

     }
              

    
   
}


