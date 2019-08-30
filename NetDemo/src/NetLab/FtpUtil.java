package NetLab;
/**
 *@author:huangqiang
 *@date:19-8-27
 *@description:ftp工具类,实现文件的上传与下载
 */


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;



import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;


public class FtpUtil {
    //文件夹深度
    static int deepth = 0;
    //格式化输出
    static Formatter formatter = new Formatter(System.out);
    static ArrayList<FileBean> fileBeans=new ArrayList<>();
    static ConnectionPool connectionPool=new ConnectionPool(5);

    public static boolean downloadFtpFile( String ftpPath, String localPath,String targetFileName) {
        /**
         *@auothor:huangqiang
         *@date:19-8-28 上午10:10
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName]
         *@returns:void
         *@description:FTP下载文件
         */
        FTPClient ftpClient = null;
        boolean result = false;
        Scanner scanner = new Scanner(System.in);

        try {
            //获得连接
            ftpClient=connectionPool.getConnection();
            //基础设置
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            //下载文件
            System.out.print("输入本地存储的文件名：");
            String localFileName = scanner.nextLine();
            File localFile = new File(localPath + File.separatorChar + localFileName);
            //判断文件是否已经存在。
            while (true) {
                if (localFile.exists()) {
                    //覆盖原文件
                    System.out.println("文件已经存在，是否覆盖？ y/n");
                    String choose = scanner.nextLine();
                    //  System.out.println(choose);
                    if (choose.equals("y")) {
                        break;
                    } else {
                        //重命名新文件
                        if (choose.equals("n")) {
                            System.out.println("请输入新的文件名：");
                            localFileName = scanner.nextLine();
                            localFile = new File(localPath + File.separatorChar + localFileName);
                        }
                    }
                } else {
                    break;
                }
            }
            //存储文件
            OutputStream os = new FileOutputStream(localFile);
            result = ftpClient.retrieveFile(targetFileName, os);
            os.close();
            connectionPool.closeClient(ftpClient);
        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取错误。");
            e.printStackTrace();
        }
        if (result){
            System.out.println("下载成功！");
        }
        return result;
    }

    public static boolean uploadFile( String ftpPath, String fileName, InputStream input) {
        /**
         *@auothor:huangqiang
         *@date:19-8-28 上午10:10
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input]
         *@returns:boolean
         *@description:FTP上传文件
         */
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            //获得连接
            int reply;
            ftpClient=connectionPool.getConnection();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            //基础设置
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            //上传文件
            ftpClient.storeFile(fileName, input);
            input.close();
            connectionPool.closeClient(ftpClient);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public static boolean deleteFile( String pathname, String filename) {
        /**
         *@auothor:huangqiang
         *@date:19-8-29 上午9:40
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, pathname, filename]
         *@returns:boolean
         *@description:删除文件
         */
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            //获得连接
            int reply;
            ftpClient=connectionPool.getConnection();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            //基础设置，开始删除文件
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            connectionPool.closeClient(ftpClient);
            success = true;
            if (success){
                System.out.println("删除文件成功");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public static void browser(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String pathname, String localPath) {

        /**
         *@auothor:huangqiang
         *@date:19-8-29 下午5:36
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, pathname, localPath]
         *@returns:void
         *@description:查询文件
         */
        FTPClient myFTPClient = null;
        //获得连接
        int reply;
        try {
            myFTPClient=connectionPool.getConnection();
            reply = myFTPClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                myFTPClient.disconnect();
            }
            //基础设置。
            myFTPClient.enterLocalPassiveMode();
            myFTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            myFTPClient.setControlEncoding("UTF-8");
            myFTPClient.changeWorkingDirectory(pathname);
            FTPFile[] files = myFTPClient.listFiles();
            String blocks = "";
            for (int i = 0; i < deepth; ++i) {
                blocks += "---";
            }
            System.out.println("Path:"+pathname);
            //显示当前文件路径下文件。
            System.out.print(blocks);
            formatter.format("%-5s %-17s %-10s %-10s\n", "编号", "文件名", "文件类型","大小");
            //  System.out.println(blocks+" "+"编号\t\t文件名\t\t\t文件类型");
            blocks += ">";
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    System.out.print(blocks + " " + i);
                    formatter.format("\t   %-20s %-10s %-10s\n", files[i].getName(), "文件",files[i].getSize()/2014+"KB");
                } else {
                    System.out.print(blocks + " " + i);
                    formatter.format("\t   %-20s %-10s %-10s\n", files[i].getName(), "文件夹",files[i].getSize()/2014+"KB");
                }
            }
            connectionPool.closeClient(myFTPClient);
            //下一步路径选择
            System.out.print("输入要浏览文件夹或者文件编号(-1 返回上一层,-2 结束访问)：");
            Scanner scanner = new Scanner(System.in);
            int index = Integer.parseInt(scanner.nextLine());
            if (index == -1) {
                //返回上一级
                deepth--;
                if (deepth >= 0) {
                    int sepIndex = pathname.lastIndexOf(File.separator);
                    pathname = pathname.substring(0, sepIndex);
                    // System.out.println(pathname);
                } else {
                    //根目录下无法再返回
                    System.err.println("已在根目录，无法继续返回上一层！");
                }
                //返回上一级
                browser(ftpHost, ftpUserName,
                        ftpPassword, ftpPort, pathname, localPath);
            } else {
                //推出查询
                if (index == -2) {
                    System.exit(0);
                } else {
                    //查寻下一级文件夹
                    if (files[index].isDirectory()) {
                        deepth++;
                        browser(ftpHost, ftpUserName,
                                ftpPassword, ftpPort, pathname + File.separator + files[index].getName(), localPath);
                    } else {
                        //文件操作
                        System.out.print("选择下载或者删除文件 0：下载，1：删除 ");
                        int choose = Integer.parseInt(scanner.nextLine());
                        //下载
                        if (choose == 0) {
                            downloadFtpFile( pathname, localPath,files[index].getName());
                        } else {
                            //删除
                            deleteFile( pathname, files[index].getName());
                        }
                        //显示当前目录文件列表
                        browser(ftpHost, ftpUserName, ftpPassword, ftpPort, pathname, localPath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listFiles(String pathname) {

        /**
         *@auothor:huangqiang
         *@date:19-8-29 下午8:50
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, pathname]
         *@returns:void
         *@description:获得所有文件列表。
         */
        FTPClient ftpClient = null;
        //获得连接
        int reply;
        try {
            ftpClient=connectionPool.getConnection();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            //基础设置。
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式，以二进制流的方式读取
            ftpClient.setControlEncoding("UTF-8");
            System.out.println(ftpClient.printWorkingDirectory());
            ftpClient.changeWorkingDirectory(pathname);
            //获得当前路径下文件列表
            FTPFile[] files = ftpClient.listFiles();
            connectionPool.closeClient(ftpClient);
            //存储文件的信息，包括名称、大小、路径、文件类型
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String name=files[i].getName();
                    //文件类型判断
                    String type;
                    if (name.lastIndexOf(".")==-1){
                        type=name;
                    }else {
                        type=name.substring(name.lastIndexOf(".")+1);
                    }
                    //存储文件了信息。
                    FileBean fileBean=new FileBean(name,pathname,type,files[i].getSize()/1024);
                    fileBeans.add(fileBean);
                }else {
                    listFiles(pathname+File.separator+files[i].getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static  void search(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort, String pathname,String localPath){
        /**
         *@auothor:huangqiang
         *@date:19-8-29 下午8:53
         *@parms:[ftpHost, ftpUserName, ftpPassword, ftpPort, pathname, localPath]
         *@returns:void
         *@description:查询文件。
         */

        listFiles(pathname);
        //显示全部文件
        formatter.format("%-3s %-39s %-6s %-54s %-5s\n", "编号", "文件名", "文件类型","路径","文件大小");
        for (int i=0;i<fileBeans.size();++i){
            FileBean fileBean=fileBeans.get(i);
            formatter.format("%-5s %-40s %-10s %-55s %-10s\n", i, fileBean.getName(), fileBean.getType(),fileBean.getPath(),fileBean.getSize()+"KB");
        }
        Scanner scanner=new Scanner(System.in);
        System.out.print("要查找的文件名(输入 -1 退出)：");
        String name= scanner.nextLine();
        try {
            if(Integer.parseInt(name)==-1){
                return;
            }
        }catch (Exception e){
            //Null
        }
        formatter.format("%-3s %-39s %-6s %-54s %-5s\n", "编号", "文件名", "文件类型","路径","文件大小");
        //查找文件
        int count=0;
        for (int i=0;i<fileBeans.size();++i){
            FileBean fileBean=fileBeans.get(i);
            if (fileBean.getName().equals(name)){
                count++;
                formatter.format("%-5s %-40s %-10s %-55s %-10s\n", i, fileBean.getName(), fileBean.getType(),fileBean.getPath(),fileBean.getSize()+"KB");
            }
        }
        //查找结果处理
        if (count==0){
            System.out.println("未找到该文件！");
            System.out.println("是否重新查找：y/n");
            String choose = scanner.nextLine();
            //  重新查找
            if (choose.equals("y")) {
                fileBeans=new ArrayList<>();
                search(ftpHost,ftpUserName,ftpPassword,ftpPort,pathname,localPath);
            } else {
                //结束查找
                if (choose.equals("n")) {
                    return;
                }
            }
        }else {
            //选择目标文件
            System.out.print("选择文件：");
            int index=Integer.parseInt(scanner.nextLine());
            FileBean target=fileBeans.get(index);
            //目标文件操作
            System.out.print("选择下载或者删除文件 0：下载，1：删除 ");
            int choose = Integer.parseInt(scanner.nextLine());
            //下载
            if (choose == 0) {
                downloadFtpFile( target.getPath(), localPath,target.getName());
            } else {
                //删除
                deleteFile(target.getPath(), target.getName());
            }
            fileBeans=new ArrayList<>();
            search(ftpHost,ftpUserName,ftpPassword,ftpPort,pathname,localPath);
        }
    }
}




