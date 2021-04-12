/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author 
 */
public class XFile {   
    public static byte[] read(String path){
        try{
            FileInputStream fis = new FileInputStream(path);
            int n = fis.available();
            byte[] data = new byte[n];
            fis.read(data);
            fis.close();
            return data;
        }catch(Exception e){
           throw new RuntimeException(e);
        }
    }
    public static void write(String path, byte[] data){
        try{
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data);
            fos.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    // Hàm readObject(String path)
    // - input đầu vào : đường dẫn và tên file ... ví dụ e:/student.dat
    // - output đầu ra : là đối tượng Object 
    public static Object readObject(String path){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object object = ois.readObject(); // đọc đối tượng
            ois.close();  // đọc xong đóng lại
            return object; // trả về Object đã đọc từ file
        }catch(Exception e){
            throw new RuntimeException(e);
        }       
    }
    // Hàm writeObject(String path, Object object)
    // - Ghi đối tượng object xuống file path
    public static void writeObject(String path, Object object){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(object); // ghi đối tượng xuống file
            oos.close();  // ghi xong rồi nhớ đóng lại.
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
