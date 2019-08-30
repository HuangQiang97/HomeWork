package NetLab;


import java.io.IOException;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @创建人：黄强
 * @时间 ：2018/8/15 23:54
 * @描述 ：TCO/UDP接收端。
 */
public class BasicFunctionRec {
    public static void main(String[] args) {
      TCPRec();
      //UDPRec();
    }
    /**
     *@auothor:huangqiang
     *@date:19-8-27 上午9:37
     *@parms:[]
     *@returns:void
     *@description:TCP多线程处理请求
     */
    private static void TCPRec() {
        Socket clientSocket=null;
        //初始化服务器套接字
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(23333);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建线程池，核心池：5，最大线程数：10，最大空闲存活时间：200，最大空闲存活时间单位，阻塞线程容器大小：5
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        while (true) {
            try {
                //接受请求，获得客户端套接字
                clientSocket = serverSocket.accept();
                //把线程请求交给线程池，并发处理请求。
                executor.execute(new TCPThreadServer(clientSocket));
                System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                        executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void UDPRec(){
        /**
         *@auothor:huangqiang
         *@date:19-8-27 上午10:39
         *@parms:[]
         *@returns:void
         *@description:UDP接收消息。
         */
        while (true) {
            //服务器套接字
            DatagramSocket datagramSocket = null;
            try {
                //初始化套接字信息。
                byte message[] = new byte[1024];
                datagramSocket = new DatagramSocket(23333);
                DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
                String backMsg = "";
                //接受客户端套接字
                datagramSocket.receive(datagramPacket);
                //获得客户端相关信息
                InetAddress IP = datagramPacket.getAddress();
                int Port = datagramPacket.getPort();
                //打印相关消息
                System.out.println("Rec Msg:" + new String(message, 0, datagramPacket.getLength()) + " from UDP client"+" \tsrc IP:" + IP + "\tsrc Port:" + Port);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                //通过类型转换判断发送的是否是日期消息
                try {
                    Date date = simpleDateFormat.parse(new String(message, 0, datagramPacket.getLength()));
                    Calendar calendar = Calendar.getInstance();
                    backMsg = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + ":" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)+" from UDP Server";
                } catch (ParseException e) {
                    backMsg = "hello"+" from UDP Server";
                }
                //构建发送packet。
                datagramPacket = new DatagramPacket(backMsg.getBytes(), backMsg.length(), IP, Port);
                datagramSocket.send(datagramPacket);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭资源
                datagramSocket.close();
            }
        }
    }
}
