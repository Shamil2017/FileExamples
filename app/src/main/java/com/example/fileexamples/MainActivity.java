package com.example.fileexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] students = {"Иванов Петр", "Петров Иван","Сидорова Мария"};
    String[] subjects = {"История","Физика","Математика"};
    ArrayList<StudMark> grades = new ArrayList<StudMark>();
    StudMark student;
    int k1,k2,k3;
    ListView lvAll;
    ArrayAdapter<StudMark> adap;
    FileOutputStream fos;
    OutputStreamWriter osw;
    BufferedWriter bw;
    String fileName = "Assessments.json";
    private static Context context = null;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goFill();
        lvAll = findViewById(R.id.lvAll);
        adap = new ArrayAdapter<StudMark>(this, android.R.layout.simple_list_item_1, grades);
        lvAll.setAdapter(adap);
        settings = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId ==R.id.writeFile)
        {
            goWriteInternalFile();
        }

        if (itemId ==R.id.readFile)
        {
            goReadInternalFile();
        }
        if (itemId ==R.id.ClearList)
        {
            goClearList();
        }
        if (itemId == R.id.SaveSharePreference)
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString( "name", "Android" );
            editor.commit();
        }
        if (itemId == R.id.LoadSharePreference)
        {
            String s1 = settings.getString("name", "");
            Toast.makeText(this, s1, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goClearList() {
        grades.clear();
        adap.notifyDataSetChanged();
    }

    private void goReadInternalFile() {
        FileInputStream is;
        InputStreamReader isr;
        BufferedReader br;
        try {
            is = openFileInput(fileName);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String temp = "", all = "";
            while ((temp=br.readLine())!=null)
            {
                JSONObject obj = new JSONObject(temp);
                grades.add(new StudMark(obj.getString("name"), obj.getString("subject"),
                             obj.getInt("grade")));

            }
            adap.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void goWriteInternalFile() {
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            JSONObject jsonObject= new JSONObject();

            for (int i=0; i< grades.size(); i++)
            {
                try {
                    jsonObject.put("name", grades.get(i).getName());
                    jsonObject.put("subject", grades.get(i).getSubject());
                    jsonObject.put("grade", grades.get(i).getGrade());

                    bw.write(jsonObject.toString()+"\n");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private  void goFill()
    {
        for (int i=0; i<20; i++)
        {
            k1 = (int) (3*Math.random());
            k2 = (int)(3*Math.random());
            k3 = (int) (5*Math.random());
            student = new StudMark(students[k1],subjects[k2],k3);
            grades.add(student);
        }
    }
}