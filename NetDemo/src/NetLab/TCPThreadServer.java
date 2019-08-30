package NetLab;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @创建人：黄强
 * @时间 ：2019/8/26 23:54
 * @描述 ：TCP多线程处理实现
 */
public class TCPThreadServer implements  Runnable{
    Socket socket;

    public TCPThreadServer(Socket clientSocket){
        this.socket =clientSocket;
    }
    @Override

/**
 *@auothor:huangqiang
 *@date:19-8-27 上午9:39
 *@parms:[]
 *@returns:void
 *@description:重写run方法，用于处理链接处理。
 */
    public void run(){
        //回显消息
        String backMSG="" ;
        String name="";
        try {
           //Thread.sleep(3000);
            //Thread.activeCount();
            Thread t = Thread.currentThread();
             name = t.getName();
            //获得客户端请求消息
            InputStream inputStream = socket.getInputStream();
            byte[] data = new byte[1024];
            int length = inputStream.read(data);
            System.out.println("Rec MSG:" + new String(data, 0, length)  + " from UDP client"+ " from  Client IP:" + socket.getInetAddress().getHostAddress()+" 处理线程："+name);
            //通过String 到Date类型的转换来判断发送消息是否是日期消息。
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
            Date date = simpleDateFormat.parse(new String(data, 0, length));
            //回显消息
            Calendar calendar = Calendar.getInstance();
            backMSG = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + ":" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)+" from TCP Server"+" 处理线程："+name;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            //发送非日期消息时的回显消息
            backMSG = "Hello"+" from TCP Server"+" 处理线程："+name;
        } finally {
            //关闭资源
            try {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(backMSG.getBytes());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
