package NetLab;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FtpTest {
    public static void main(String[] args) {
        //配置信息
        String ftpHost = "127.0.0.1";
        String ftpUserName = "huangqiang";
        String ftpPassword = "huangqiang";
        int ftpPort = 21;
        //ftp服务器上文件夹路径
        String ftpPath = "data";
        //本地文件路径。上传：全路径，带文件名。下载：要存储的文件夹路径。
        String localPath = "/home/huangqiang/IdeaProjects/NetDemo/media/";
        //表示上传ftp服务器端名称。
        String targetFileName = "pink.jpg";

        //上传一个文件
        try{
            FileInputStream in=new FileInputStream(new File(localPath));
            boolean test = FtpUtil.uploadFile( ftpPath, targetFileName,in);
            if(test){
                System.out.println("上传成功");
            }else {
                System.out.println("上传失败");
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println(e);
        }

        //   下载一个文件
        boolean result=FtpUtil.downloadFtpFile( ftpPath, localPath,targetFileName);
        if (result){
            System.out.println("下载成功！");
        }else {
            System.out.println("下载失败！");
        }

        //删除文件
        FtpUtil.deleteFile( ftpPath,targetFileName);

        //浏览目录
       FtpUtil.browser(ftpHost,ftpUserName,ftpPassword,ftpPort,ftpPath,localPath);

        //文件查找
        FtpUtil.search(ftpHost,ftpUserName,ftpPassword,ftpPort,ftpPath,localPath);

    }
}