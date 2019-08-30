package NetLab;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Calendar;

/**
 * @创建人：黄强
 * @时间 ：2018/8/15 23:54
 * @描述 ：TCP/UDP发送端
 */
public class BasicFunctionSend {

    public static void main(String[] args) {
        //初始化发送日期消息
        Calendar calendar=Calendar.getInstance();
        String msg=""+calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE)+":"+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
        //发送普通消息
       //  msg="sdghs";
        TCPSend(msg);
        //UDPSend(msg);
    }
    private static  void TCPSend(String msg){
        /**
        *@auothor:huangqiang
        *@date:19-8-27 上午9:36
        *@parms:[msg]
        *@returns:void
        *@description:TCP消息发送
        */
        System.out.println("Sent msg:"+msg);
        Socket clientSocket=null;
        byte data[]=new byte[1024];
        try {
            //初始化服务器地址和端口
            byte [] serverIP={127,0,0,1};
            clientSocket = new Socket(InetAddress.getByAddress(serverIP), 23333);
            //向服务器发消息
            OutputStream outputStream=clientSocket.getOutputStream();
            outputStream.write(msg.getBytes());
            //获得服务器回复消息
            InputStream inputStream=clientSocket.getInputStream();
            int length=inputStream.read(data);
            System.out.println("Back MSG:"+new String(data,0,length)+"  from :Server IP:"+clientSocket.getInetAddress().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static  void UDPSend(String msg){
        /**
        *@auothor:huangqiang
        *@date:19-8-27 上午10:40
        *@parms:[msg]
        *@returns:void
        *@description:UDP发送消息
        */
        System.out.println("Sent msg:"+msg);
        //客户端套接字
        DatagramSocket datagramSocket =null;
        try {
            //初始化收方IP
            byte IPAddress []={(byte)127,0,0,1};
            InetAddress inetAddress=InetAddress.getByAddress(IPAddress);
            byte[] reciveBuffer=new byte[1024];
            //实例化socket对象。
            datagramSocket=new DatagramSocket();
            //构建数据。
            DatagramPacket sendPacket=new DatagramPacket(msg.getBytes(),msg.length(),inetAddress,23333);
            //发送。
            datagramSocket.send(sendPacket);
            //构建接受Packet。
            DatagramPacket recivePacket=new DatagramPacket(reciveBuffer,reciveBuffer.length);
            //接受消息。
            datagramSocket.receive(recivePacket);
            System.out.println("back MSG:"+new String(reciveBuffer,0,recivePacket.getLength()));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭套接字
            datagramSocket.close();
        }
    }
}
