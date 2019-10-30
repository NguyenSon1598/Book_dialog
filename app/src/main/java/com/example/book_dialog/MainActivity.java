package com.example.book_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnthongtinsach;
    Dialog dialog;

    EditText etbook, ettitle, etidauthor;
    Button bt_save, bt_select, bt_update, bt_delete;
    GridView gv_display;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnthongtinsach = (Button) findViewById(R.id.btnThongtinsach);

        btnthongtinsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuDialog();
            }
        });
    }

    private void showMenuDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.book_dialog);
        dialog.setTitle("Thông tin sách");

        etbook = (EditText)dialog.findViewById(R.id.edtNhapmasach);
        ettitle = (EditText)dialog.findViewById(R.id.edtNhaptieude);
        etidauthor = (EditText)dialog.findViewById(R.id.edtTentacgia);
        gv_display = (GridView)dialog.findViewById(R.id.grvList);
        dbHelper = new DBHelper(this);
        bt_delete=(Button)dialog.findViewById(R.id.btnDelete);
        bt_save = (Button)dialog.findViewById(R.id.btnSave);
        bt_update=(Button)dialog.findViewById(R.id.btnUpdate);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ettitle.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tiêu đề sách", Toast.LENGTH_SHORT).show();
                }
                else if (etidauthor.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập id tác giả", Toast.LENGTH_SHORT).show();
                }
                else{
                    Book book = new Book();
                    book.setId_book(Integer.parseInt(etbook.getText().toString()));
                    book.setTitle(etidauthor.getText().toString());

                    book.setId_author(Integer.parseInt(etidauthor.getText().toString()));

                    if (dbHelper.insertBook(book))
                        Toast.makeText(getApplicationContext(), "Đã lưu thành công", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(getApplicationContext(), "Không lưu thành công", Toast.LENGTH_SHORT).show();
                }

            }


        });

        bt_select = (Button)dialog.findViewById(R.id.btnSelect);
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Book> books = new ArrayList<>();
                books = dbHelper.getAllBook();
                for (Book b : books) {
                    list.add(b.getId_book() + "");
                    list.add(b.getTitle() + "");
                    list.add(b.getId_author() + "");
                }
                Adapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                gv_display.setAdapter((ListAdapter) adapter);
                if (gv_display.getCount()==0){
                    Toast.makeText(getApplicationContext(),"Danh sách rỗng",Toast.LENGTH_SHORT).show();
                }

            }
        });


        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_id = etbook.getText().toString();
                if(book_id.length() >= 1) {

                    Boolean st = dbHelper.deleteBook(Integer.parseInt(etbook.getText().toString()));

                    if (st == true)
                        Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Book> books = new ArrayList<>();
                books = dbHelper.getAllBook();
                for (Book b : books) {
                    list.add(b.getId_book() + "");
                    list.add(b.getTitle() + "");
                    list.add(b.getId_author() + "");
                }
                Adapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                gv_display.setAdapter((ListAdapter) adapter);
                etbook.setText("");
                ettitle.setText("");
                etidauthor.setText("");

            }
        });


        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ettitle.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tiêu đề sách", Toast.LENGTH_SHORT).show();
                }
                else if (etidauthor.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập id tác giả", Toast.LENGTH_SHORT).show();
                }
                else{
                    Book book = dbHelper.getBook(Integer.parseInt(etbook.getText().toString()));
                    book.setId_book(Integer.parseInt(etbook.getText().toString()));
                    book.setTitle(ettitle.getText().toString());
                    book.setId_author(Integer.parseInt(etidauthor.getText().toString()));
                    if (dbHelper.updateBook(book))
                        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
                ArrayList<String> list = new ArrayList<>();
                ArrayList<Book> books = new ArrayList<>();
                books = dbHelper.getAllBook();
                for (Book b : books) {
                    list.add(b.getId_book() + "");
                    list.add(b.getTitle() + "");
                    list.add(b.getId_author() + "");
                }
                Adapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                gv_display.setAdapter((ListAdapter) adapter);
                etbook.setText("");
                ettitle.setText("");
                etidauthor.setText("");
            }
        });
        dialog.show();
    }


}
