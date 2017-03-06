import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by XXXX on 2017/2/17.
 */
public class test {
    public static void main(String args[]) {
        char[] symbol = {'+','-','*','/'};
        int num = 0;
        int i = 0, count = 0,a1,a2,b1,b2;
        String result="";
        System.out.print("请输入题数:");
        Random random = new Random();
        num = Integer.parseInt(input(0));//输入题数
        for (i=0;i<num;i++){
            a2=Math.abs(random.nextInt()%29)+1;// 分母1
            b2=Math.abs(random.nextInt()%29)+1;//分母2
            a1=Math.abs(random.nextInt()%29)+1;
            b1=Math.abs(random.nextInt()%29)+1;
            if (a2!=1&&a1>a2){
                a1=Math.abs(random.nextInt()%(a2-1)+1);
            }
            if (b2!=1&&b1>b2){
                b1=Math.abs(random.nextInt()%(b2-1)+1);
            }
            char s = symbol[random.nextInt(4)];
            if (b1==0)
                s = symbol[random.nextInt(3)];

            int divisor = gcd(a1,a2);
            if (divisor>1){
                a1 = a1/divisor;
                a2 = a2/divisor;
            }
            divisor = gcd(b1,b2);
            if(divisor>1){
                b1 = b1/divisor;
                b2 = b2/divisor;
            }
            print(a1,a2,b1,b2,s);

            //a1=6;a2=7;b1=1;b2=4;s='-';
            result = calculate(a1,a2,b1,b2,s);//计算正确答案
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
    public static String input(int n){
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
    public static String calculate(int a1,int a2,int b1,int b2,char s){
        String result="";
        int max;
        if(s=='*'){
            max=gcd(a1*b1,a2*b2);
            result = a1*b1/max+ "/" + a2*b2/max;
        }
        else{
            int c1=a1*b2,c2=a2*b1;
            switch(s){
                case '+':  max=gcd(c1+c2,a2*b2);
                            result= (c1+c2)/max + "/" + a2*b2/max; break;
                case '-':  max=gcd(c1-c2,a2*b2);
                            result = (c1-c2)/max + "/" + a2*b2/max; break;
                case '/':max=gcd(c1,c2);result = c1/max+ "/" +c2/max; break;
            }
        }
        return result;
    }
    public static void print(int a1,int a2,int b1,int b2,char s){       //算术题输出
        if (a1%a2==0){
            System.out.print(a1/a2+" "+s+"");
        }
        else{
            if (a1==0){
                System.out.print(0);
            }
            else System.out.print(a1);
            if (a2!=1){
                System.out.print("/"+a2+" "+s+" ");
            }
            else
                System.out.print(" "+s+" ");
        }
        if (b1%b2==0){
            System.out.print(b1/b2+" "+"= ");
        }
        else{
            if (b1==0){
                System.out.print(0);
            }
            else System.out.print(b1);
            if (b2!=1){
                System.out.print("/"+b2+" "+"= ");
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
    public static boolean check(String ans,String result){      //答案判断
        String[] res_ar=result.split("/");
        ans = ans.replace(" ","");
        if (ans.contains("/")){
            String[] ans_ar=ans.split("/");
            if (ans_ar[1].equals("0"))
                return false;
            int m=gcd(Integer.parseInt(ans_ar[0]),Integer.parseInt(ans_ar[1]));
            ans=Integer.parseInt(ans_ar[0])/m+"/"+Integer.parseInt(ans_ar[1])/m;
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
