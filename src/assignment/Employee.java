package assignment;

import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Employee implements Serializable {

    private String maNV;
    private String hoTen;
    private int tuoi, luong;
    private String email;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) throws Exception {
        if (maNV.equalsIgnoreCase("")) {
            //ném ra lỗi để main bắt lỗi rồi chửi
            throw new Exception("Mã không được để trống");
        } else {
            this.maNV = maNV;
        }

    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) throws Exception {
        //1. Tên khác rỗng mới hợp lệ
        if (hoTen.equalsIgnoreCase("")) {
            //ném ra lỗi để main bắt lỗi rồi chửi
            throw new Exception("Tên không được để trống");
        } else {
            this.hoTen = hoTen;
        }

    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) throws Exception {

        if (tuoi < 17 || tuoi > 65) {
            throw new Exception("Tuổi phải từ 17-65");

        } else {
            this.tuoi = tuoi;
        }
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;

    }

    public Employee() {

    }

    public Employee(String maNV, String hoTen, int tuoi, String email, int luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.email = email;
        this.luong = luong;
    }

}
