import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Created by XXXX on 2017/2/17.
 */
public class test {
    public static void main(String args[]) {

        String []quiz = new String[4];
        int num = 0;
        int i = 0, count = 0,numerator1=0,denominator1=0,numerator2=0,denominator2=0;
        String s="";
        String result;
        result = "";
        System.out.print("请输入题数:");

        num = Integer.parseInt(input(0));//输入题数
        for (i=0;i<num;i++){
            String str = ProduceQuiz();
            while(repeat(str,quiz)){        //判断题目是否重复
                str = ProduceQuiz();
            }
            String []number = str.split(",");
            numerator1 = Integer.parseInt(number[0]);
            denominator1 = Integer.parseInt(number[1]);
            numerator2 = Integer.parseInt(number[3]);
            denominator2 = Integer.parseInt(number[4]);
            s = number[2];
            print(numerator1,denominator1,numerator2,denominator2,s);
            //a1=7;a2=8;b1=1;b2=4;s="*";
            result = calculate(numerator1,denominator1,numerator2,denominator2,s);//计算正确答案
            //System.out.print(result);
            String ans = input(1);//答案输入
            if(check(ans,result)){
                System.out.println("Correct");
                count++;
            }
            else
                System.out.println("Wrong");
        }
        System.out.println("正确率:"+count +"/"+ num);

    }
    public static boolean repeat(String str,String quiz[]) {
        String question[]=str.split(",");
            switch (question[2]){
                case "+":
                            if (quiz[0]==null){
                            quiz[0] = str.replace(",","")+"/";
                            return false;
                            }
                            return FindQuiz(quiz,str);
                case "-": if (quiz[1]==null){
                            quiz[1] = str.replace(",","")+"/";
                            return false;
                            }
                            return FindQuiz(quiz,str);
                case "*": if (quiz[2]==null){
                            quiz[2] = str.replace(",","")+"/";
                            return false;
                            }
                            return FindQuiz(quiz,str);
                case "/": if (quiz[3]==null){
                            quiz[3] = str.replace(",","")+"/";
                            return false;
                            }
                            return FindQuiz(quiz,str);
            }
            return true;
        }
    public static boolean FindQuiz(String quiz[],String str){
            String question[] = str.split(",");
            String str2;
            str2 = str.replace(",","")+"/";
            switch (question[2]){
                case "+": if (quiz[0].contains(str2)){
                                return true;
                            }
                            else if(quiz[0].contains(question[3]+question[4]+question[2]+question[0]+question[1]+"/")){
                                return true;
                            }
                            else{
                                quiz[0] = quiz[0].concat(str2);
                                return false;
                            }
                case "-": if (quiz[1].contains(str2)){
                                return true;
                            }
                            else{
                            quiz[1] = quiz[1].concat(str2);
                            return false;
                            }
                case "*": if (quiz[2].contains(str2)){
                                return true;
                            }
                            else if(quiz[2].contains(question[3]+question[4]+question[2]+question[0]+question[1]+"/")){
                                return true;
                            }
                            else{
                                quiz[2] = quiz[2].concat(str2);
                                return false;
                            }
                case "/": if (quiz[3].contains(str2)){
                                return true;
                                }
                            else{
                                quiz[3] = quiz[3].concat(str2);
                                return false;
                            }

            }
            return true;
        }
    public  static String  ProduceQuiz(){
        char[] symbol = {'+','-','*','/'};
        int numerator1,denominator1,numerator2,denominator2;
        char s;
        Random random = new Random();
        denominator1=Math.abs(random.nextInt()%29)+1;// 分母1
        denominator2=Math.abs(random.nextInt()%29)+1;//分母2
        numerator1=Math.abs(random.nextInt()%29)+1;
        numerator2=Math.abs(random.nextInt()%29)+1;
        if (denominator1!=1&&numerator1>denominator1){
            numerator1=Math.abs(random.nextInt()%(denominator1-1)+1);
        }
        if (denominator2!=1&&numerator2>denominator2){
            numerator2=Math.abs(random.nextInt()%(denominator2-1)+1);
        }
        s = symbol[random.nextInt(4)];
        if (numerator2==0)
            s = symbol[random.nextInt(3)];
        int divisor = gcd(numerator1,denominator1);   // 求分子分母间最大公约数
        if (divisor>1){
            numerator1 = numerator1/divisor;
            denominator1 = denominator1/divisor;
        }
        divisor = gcd(numerator2,denominator2);
        if(divisor>1){
            numerator2 = numerator2/divisor;
            denominator2 = denominator2/divisor;
        }//题目分数化简
        return numerator1 +","+denominator1+","+s+","+numerator2+","+denominator2;
    }
    public static String input(int n){              //题数及答案输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String num;
        while(true){
            try{
                num = br.readLine();
                switch(n){
                    case 0:Integer a = Integer.valueOf(num);
                            while(a<=0){
                                System.out.print("请输入大于0的整数");
                                num = br.readLine();
                                a = Integer.valueOf(num);
                            }
                            break;
                    case 1:return num;
                }

                return num;

            }catch(Exception e){
                System.out.println("请重新输入");
            }
        }

    }
    public static String calculate(int numerator1,int denominator1,int numerator2,int denominator2,String s){         //题目答案计算
        String result="";
        int max;
        if(s.equals("*")){
            max=gcd(numerator1*numerator2,denominator1*denominator2);
            result = numerator1*numerator2/max+ "/" + denominator1*denominator2/max;
        }
        else{
            int c1=numerator1*denominator2,c2=denominator1*numerator2;
            switch(s){
                case "+":  max=gcd(c1+c2,denominator1*denominator2);
                            result= (c1+c2)/max + "/" + denominator1*denominator2/max; break;
                case "-":  max=gcd(c1-c2,denominator1*denominator2);
                            result = (c1-c2)/max + "/" + denominator1*denominator2/max; break;
                case "/":max=gcd(c1,c2);result = c1/max+ "/" +c2/max; break;
            }
        }
        return result;
    }
    public static void print(int numerator1,int denominator1,int numerator2,int denominator2,String s){       //算术题输出
        if (numerator1%denominator1==0){
            System.out.print(numerator1/denominator1+" "+s+" ");
        }
        else{
            if (numerator1==0){
                System.out.print(0);
            }
            else System.out.print(numerator1);
            if (denominator1!=1){
                System.out.print("/"+denominator1+" "+s+" ");
            }
            else
                System.out.print(" "+s+" ");
        }
        if (numerator2%denominator2==0){
            System.out.print(numerator2/denominator2+" "+"= ");
        }
        else{
            if (numerator2==0){
                System.out.print(0);
            }
            else System.out.print(numerator2);
            if (denominator2!=1){
                System.out.print("/"+denominator2+" "+"= ");
            }
            else
                System.out.print(" "+"= ");
        }

    }
    public static int gcd(int a,int b) {        //辗转相除
        int k=0;
        do {
            k=a%b;
            a=b;
            b=k;
        }while(k!=0);
        a=Math.abs(a);
        return a;
    }
    public static boolean isPrime(int n) {
        while(n!=1){
            if (n%5==0){
                if(isPrime(n/5))
                    return true;
            }
            if (n%2==0){
                if(isPrime(n/2))
                    return true;
            }

            return false;
        }
        if (n==1)
            return true;
        return false;
    }
    public static boolean check(String ans,String result){
        String res_array[] = result.split("/");
        Double answer,res;
        if (!ans.contains("."))
            return FractionCheck(ans,result);
        ans = ans.replace(" ","");
        res = Double.parseDouble(res_array[0])/Double.parseDouble(res_array[1]);
        String resStr = res.toString();     //计算出的小数答案string
       try{
           answer = Double.parseDouble(ans);        //若成功转换为double
           if (isPrime(Integer.parseInt(res_array[1]))){        //判断分母是否由2,5组成
                while(resStr.length()<ans.length())
                    resStr = resStr+'0';
                if(resStr.compareTo(ans)==0){
                    return true;
                }
           }
           else if (resStr.length()>ans.length()){        //若计算的答案比输入的长         先将计算出的答案四舍五入到和输入同长度在对比
               resStr = resStr.substring(0,ans.length()+1);
               if (resStr.charAt(ans.length())>='5'){
                    if (resStr.charAt(ans.length())=='9'){
                        resStr = resStr.substring(0,ans.length()-1)+(char)((int)resStr.charAt(ans.length()-1)+1);
                    }
                    else{
                        resStr = resStr.substring(0,ans.length()-1)+(char)((int)resStr.charAt(ans.length()-1)+1);
                    }
               }
               else{
                    resStr = resStr.substring(0,resStr.length()-1);
               }
           }

           if(resStr.compareTo(ans)==0){
               return true;
           }
       }catch (Exception e){
           FractionCheck(ans,result);
       }
        return false;
    }
    public static boolean FractionCheck(String ans,String result){      //答案判断
        String[] res_ar=result.split("/");
        ans = ans.replace(" ","");
        if (ans.contains("/")){
            String[] ans_ar=ans.split("/");
            if (ans_ar[1].equals("0"))
                return false;
            try{
                int m=gcd(Integer.parseInt(ans_ar[0]),Integer.parseInt(ans_ar[1]));
                ans=Integer.parseInt(ans_ar[0])/m+"/"+Integer.parseInt(ans_ar[1])/m;
            }catch(Exception e){
                return false;
            }
            if (res_ar[1].equals("1")&&res_ar[0].equals(ans)) {
                return true;
            }
            else if (res_ar[0].equals("0")&&ans_ar[0].equals("0")){
                return true;
            }
        }
        if (ans.equals(result))
            return true;
        else if (res_ar[1].equals("1")&&res_ar[0].equals(ans)){
            return true;
        }
        else if (res_ar[0].equals(res_ar[1])&&ans.equals("1")){
            return true;
        }
        else if (res_ar[0].equals("-"+res_ar[1])&&ans.equals("-1")){
            return true;
        }
        else if (res_ar[0].equals("0")&&ans.equals("0")){
            return  true;
        }
        else
            return false;
    }
}
