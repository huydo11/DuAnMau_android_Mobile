package com.example.duanmau_android_mobile.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau_android_mobile.Adapter.LoaiSachAdapter;
import com.example.duanmau_android_mobile.DAo.LoaiSachDAO;
import com.example.duanmau_android_mobile.R;
import com.example.duanmau_android_mobile.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class LoaiSachFragment extends Fragment {
    ArrayList<LoaiSach> listLoaiSach = new ArrayList<>();
    Context context;
    private RecyclerView rcvLoaiSach;
    private FloatingActionButton fabThemLoaiSach;
    LoaiSachAdapter loaiSachAdapter;
    LoaiSachDAO loaiSachDAO;
    private EditText edTimKiem;
    private Button btnTimKiem;
    private List<LoaiSach> loaiSachesFind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvLoaiSach = (RecyclerView) view.findViewById(R.id.rcvLoaiSach);
        fabThemLoaiSach = (FloatingActionButton) view.findViewById(R.id.fabThemLoaiSach);


        edTimKiem = (EditText) view.findViewById(R.id.edTimKiem);
        btnTimKiem = (Button) view.findViewById(R.id.btnTimKiem);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvLoaiSach.setLayoutManager(layoutManager);
        loaiSachDAO = new LoaiSachDAO(getContext());
        listLoaiSach = loaiSachDAO.getAllLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(listLoaiSach);
        rcvLoaiSach.setAdapter(loaiSachAdapter);

        fabThemLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogThemLoaiSach();
            }
        });
        //tao ra 1 ist find
        loaiSachesFind = new ArrayList<>();
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = -1;
                String nccFind = edTimKiem.getText().toString();
                for (int i = 0; i < listLoaiSach.size(); i++) {
                    if (nccFind.equalsIgnoreCase(listLoaiSach.get(i).getNhaCungCap())) {
                        loaiSachesFind.clear();
                        loaiSachesFind.add(listLoaiSach.get(i));
                        pos = i;
                    }
                    if (pos != -1) {
                        Toast.makeText(getContext(), "Đã tìm thấy " + nccFind, Toast.LENGTH_SHORT).show();
                        loaiSachAdapter = new LoaiSachAdapter(loaiSachesFind);
                        rcvLoaiSach.setAdapter(loaiSachAdapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        rcvLoaiSach.setLayoutManager(layoutManager);
                    } else {
                        Toast.makeText(getContext(), "Khong tim thay " + nccFind, Toast.LENGTH_SHORT).show();
                        loaiSachAdapter = new LoaiSachAdapter(listLoaiSach);
                        rcvLoaiSach.setAdapter(loaiSachAdapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        rcvLoaiSach.setLayoutManager(layoutManager);
                    }
                }
            }
        });
    }

    private EditText edIDLoaiSach;
    private EditText edTenSach;
    private Button btnAddLoaiSach;
    private Button btnDongLoaiSach;
    private EditText edNhaCungCap;


    public void DialogThemLoaiSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();


        edIDLoaiSach = (EditText) view.findViewById(R.id.edIDLoaiSach);
        edTenSach = (EditText) view.findViewById(R.id.edTenSach);
        btnAddLoaiSach = (Button) view.findViewById(R.id.btnAddLoaiSach);
        btnDongLoaiSach = (Button) view.findViewById(R.id.btnDongLoaiSach);
        edNhaCungCap = (EditText) view.findViewById(R.id.edNhaCungCap);

        edIDLoaiSach.setEnabled(false);
        btnAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 1;
                String tenLoaiSach = edTenSach.getText().toString();
                String nhaCungCap = edNhaCungCap.getText().toString();
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setTenLoai(tenLoaiSach);
                loaiSach.setNhaCungCap(nhaCungCap);
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());


                if (!Character.isUpperCase(tenLoaiSach.codePointAt(0))) {
                    edTenSach.setError("Vui long viet hoa chu cai dau");
                    return;
                } else if (edNhaCungCap.getText().toString().length() == 0) {
                    edNhaCungCap.setError("khong duoc de trong");
                    return;
                } else if (edTenSach.getText().toString().length() == 0) {
                    edTenSach.setError("khong duoc de trong");
                    return;
                } else {
                    long check = loaiSachDAO.insertLoaiSach(loaiSach);
                    if (check > 0) {
                        Toast.makeText(getContext(), "Them Thanh Cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        listLoaiSach.clear();
                        listLoaiSach.addAll(loaiSachDAO.getAllLoaiSach());
                        loaiSachAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), "them that bai", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });
        btnDongLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}