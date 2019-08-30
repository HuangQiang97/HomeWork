package NetLab;
/**
 *@author:huangqiang
 *@date:19-8-29
 *@description:
 */

public class FileBean {
   private String name;
   private String path;
   private String type;
   private long size;

    public FileBean(String name, String path, String type, long size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long  getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



}
