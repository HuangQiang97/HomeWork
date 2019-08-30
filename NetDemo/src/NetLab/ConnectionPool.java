package NetLab;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class ConnectionPool {
    /**
     *@author:huangqiang
     *@date:19-8-30
     *@description:连接池
     */
    ArrayList<FTPClient> ftpClients=new ArrayList<>();
    //池总大小-1
    int topIndex;
    public ConnectionPool(int maxConnection) {
        topIndex =maxConnection-1;
        //配置信息
        String ftpHost = "127.0.0.1";
        String ftpUserName = "huangqiang";
        String ftpPassword = "huangqiang";
        int ftpPort = 21;
        //获得多个连接
        for (int i = 0; i< topIndex +1; ++i){
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient = new FTPClient();
                // 连接FTP服务器
                ftpClient.connect(ftpHost, ftpPort);
                // 登陆FTP服务器
                ftpClient.login(ftpUserName, ftpPassword);
                //状态查询
                if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                    System.out.println("未连接到FTP，用户名或密码错误。");
                    ftpClient.disconnect();
                } else {
                    System.out.println("FTP连接成功。");
                }
            } catch (SocketException e) {
                e.printStackTrace();
                System.out.println("FTP的IP地址可能错误，请正确配置。");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("FTP的端口错误,请正确配置。");
            }
            ftpClients.add(ftpClient);
        }
    }
    public  FTPClient getConnection(){
        /**
         *@auothor:huangqiang
         *@date:19-8-30 上午10:43
         *@parms:[]
         *@returns:org.apache.commons.net.ftp.FTPClient
         *@description:获得连接
         */
        if (topIndex >=0){
            topIndex--;
            return ftpClients.remove(topIndex +1);
        }
        System.out.println("连接池已空");
        return null;
    }
    public   void  closeClient(FTPClient ftpClient){
        /**
         *@auothor:huangqiang
         *@date:19-8-30 上午10:43
         *@parms:[]
         *@returns:void
         *@description:归还连接
         */
        try {
            ftpClient.changeWorkingDirectory("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ftpClients.add(ftpClient);
        topIndex++;
    }
    public  void destoryAll(){
        /**
         *@auothor:huangqiang
         *@date:19-8-30 上午10:43
         *@parms:[]
         *@returns:void
         *@description:摧毁所有连接
         */
        for (int i=0;i<ftpClients.size();++i){
            try {
                FTPClient f =ftpClients.get(i);
                f.logout();
                f.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
