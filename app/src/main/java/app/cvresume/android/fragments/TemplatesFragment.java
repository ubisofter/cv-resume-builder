package app.cvresume.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFootnote;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.models.Course;
import app.cvresume.android.models.Education;
import app.cvresume.android.models.Experience;
import app.cvresume.android.models.Lang;
import app.cvresume.android.models.MainInfo;
import app.cvresume.android.models.PersonalInfo;
import app.cvresume.android.models.Resume;
import app.cvresume.android.models.Skill;

public class TemplatesFragment extends Fragment {
    private AppCompatActivity activity;
    private WebView webView;
    private SharedPreferences sharedPreferences;
    private List<MainInfo> mainInfoList;
    private List<PersonalInfo> personalInfoList;
    private List<Experience> experienceList;
    private List<Education> educationList;
    private List<Course> courseList;
    private List<Lang> langList;
    private List<Skill> skillList;
    private File filePath = null;
    private Context context;

    String name, surname, secondName, email, phoneNum, resumeTitle, busy, salary, schedule;
    String city, birthDate, famStatus, citizenship, eduGrade, personalQualities, profSkills, about;
    Boolean children, gender, army, medBook, driveCat;
    String childrenValue, genderValue, armyValue, medBookValue, driveCatValue;
    String phoneNumber,website,linkedin,customField;
    String educationListJson, experienceListJson, courseListJson, langListJson, skillListJson, hobbyListJson;

    public TemplatesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_templates, container, false);
        activity = (AppCompatActivity) requireActivity();

        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                getData();
                filePath = new File(requireContext().getExternalFilesDir(Context.DOWNLOAD_SERVICE), "Test.docx");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            changeValues();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });

        return view;
    }

    public void getData() {
        name = sharedPreferences.getString("name", "");
        surname = sharedPreferences.getString("surname", "");
        secondName = sharedPreferences.getString("second_name", "");
        email = sharedPreferences.getString("email", "");
        phoneNum = sharedPreferences.getString("phone_num", "");
        resumeTitle = sharedPreferences.getString("resume_title", "");
        busy = sharedPreferences.getString("busy", "");
        salary = sharedPreferences.getString("salary", "");
        schedule = sharedPreferences.getString("schedule", "");

        city = sharedPreferences.getString("city", "");
        birthDate = sharedPreferences.getString("birth_date", "");
        famStatus = sharedPreferences.getString("fam_status", "");
        children = sharedPreferences.getBoolean("children", false);
        citizenship = sharedPreferences.getString("citizenship", "");
        gender = sharedPreferences.getBoolean("gender", false);
        eduGrade = sharedPreferences.getString("edu_grade", "");
        army = sharedPreferences.getBoolean("army", false);
        medBook = sharedPreferences.getBoolean("med_book", false);
        driveCat = sharedPreferences.getBoolean("drive_cat", false);
        personalQualities = sharedPreferences.getString("personal_qualities", "");
        profSkills = sharedPreferences.getString("prof_skills", "");
        about = sharedPreferences.getString("about", "");

        experienceListJson = sharedPreferences.getString("experience_list", "");
        educationListJson = sharedPreferences.getString("education_list", "");
        courseListJson = sharedPreferences.getString("course_list", "");
        langListJson = sharedPreferences.getString("lang_list", "");
        skillListJson = sharedPreferences.getString("skill_list", "");
        hobbyListJson = sharedPreferences.getString("hobby_list", "");

        Gson gson = new Gson();
        experienceList = gson.fromJson(experienceListJson, new TypeToken<List<Experience>>() {}.getType());
        educationList = gson.fromJson(educationListJson, new TypeToken<List<Education>>() {}.getType());
        courseList = gson.fromJson(courseListJson, new TypeToken<List<Course>>() {}.getType());
        langList = gson.fromJson(langListJson, new TypeToken<List<Lang>>() {}.getType());
        skillList = gson.fromJson(skillListJson, new TypeToken<List<Skill>>() {}.getType());

        MainInfo mainInfo = new MainInfo(name, surname, secondName, email, phoneNum, resumeTitle, busy, salary, schedule);

        PersonalInfo personalInfo = new PersonalInfo(city, birthDate, famStatus, children, citizenship, gender, eduGrade, army, medBook, driveCat, personalQualities, profSkills, about);

        personalInfoList = new ArrayList<>();
        personalInfoList.add(personalInfo);

        Resume resume = new Resume(mainInfoList, personalInfoList, experienceList, educationList, skillList, langList);
    }

    public void changeValues() throws IOException {

        try {
            Context context = requireContext();

            Resources resources = context.getResources();
            InputStream templateStream = resources.openRawResource(R.raw.template_1);
            XWPFDocument xwpfDocument = new XWPFDocument(templateStream);

            Map<String, String> replaceData = new HashMap<>();
            replaceData.put("name", name);
            replaceData.put("surname", surname);
            replaceData.put("second_name", secondName);
            replaceData.put("email", email);
            replaceData.put("phone_num", phoneNum);
            replaceData.put("resume_title", resumeTitle);
            replaceData.put("busy", busy);
            replaceData.put("salary", salary);
            replaceData.put("schedule", schedule);

            replaceData.put("city", city);
            replaceData.put("birth_date", birthDate);
            replaceData.put("fam_status", famStatus);

            if (!children){childrenValue = "Нет";}
            else { childrenValue = "Есть";}
            replaceData.put("children", childrenValue);

            replaceData.put("citizenship", citizenship);

            if (!gender){genderValue = "Мужской";}
            else { genderValue = "Женский";}
            replaceData.put("gender", genderValue);

            replaceData.put("edu_grade", eduGrade);

            if (!army){armyValue = "Нет";}
            else { armyValue = "Да";}
            replaceData.put("army", armyValue);

            if (!medBook){medBookValue = "Нет";}
            else { medBookValue = "Есть";}
            replaceData.put("med_book", medBookValue);

            if (!driveCat){driveCatValue = "Нет";}
            else { driveCatValue = "Есть";}
            replaceData.put("drive_cat", driveCatValue);

            replaceData.put("personal_qualities", personalQualities);
            replaceData.put("prof_skills", profSkills);
            replaceData.put("about", about);

            replaceData.put("employer", experienceList.get(0).getEmployer());
            replaceData.put("duration", experienceList.get(0).getDuration());
            replaceData.put("position", experienceList.get(0).getPosition());
            replaceData.put("work_desc", experienceList.get(0).getWorkdesc());

            replaceData.put("education", educationList.get(0).getDegree());
            replaceData.put("university", educationList.get(0).getUniversity());
            replaceData.put("degree", educationList.get(0).getDegree());
            replaceData.put("edu_dates", educationList.get(0).getYears());
            replaceData.put("edu_desc", educationList.get(0).getDesc());

            replaceData.put("lang", langList.get(0).getLang());
            replaceData.put("lang_lvl", String.valueOf(langList.get(0).getLangLvl()));

            replaceData.put("co_name", courseList.get(0).getCourseName());
            replaceData.put("co_school", courseList.get(0).getCourseSchool());
            replaceData.put("co_period", courseList.get(0).getCoursePeriod());
            replaceData.put("co_desc", courseList.get(0).getCourseDesc());

            replaceData.put("skill", skillList.get(0).getSkill());
            replaceData.put("skill_lvl", String.valueOf(skillList.get(0).getSkillLevel()));
            replaceData.put("skill_desc", skillList.get(0).getSkillDesc());

            replaceTextInDocument(xwpfDocument, replaceData);

            File outputDir = context.getExternalFilesDir(null);
            if (outputDir != null) {
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File outputFile = null;
                if (downloadsDir != null) {
                    outputFile = new File(downloadsDir, "Test.docx");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                xwpfDocument.write(fileOutputStream);

                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                xwpfDocument.close();
                Log.d("ДАННЫЕ РЕЗЮМЕ",
                        "\n Данные сохранены ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaceTextInDocument(XWPFDocument document, Map<String, String> replaceData) {
        for (XWPFAbstractNum abstractNum : document.getNumbering().getAbstractNums()) {
            replaceTextInNumbering(abstractNum, replaceData);
        }

        for (IBodyElement bodyElement : document.getBodyElements()) {
            replaceTextInBodyElement(bodyElement, replaceData);
        }

        for (XWPFFootnote footnote : document.getFootnotes()) {
            replaceTextInFootnote(footnote, replaceData);
        }
    }

    private void replaceTextInBodyElement(IBodyElement bodyElement, Map<String, String> replaceData) {
        if (bodyElement instanceof XWPFParagraph) {
            XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
            replaceTextInParagraph(paragraph, replaceData);
        } else if (bodyElement instanceof XWPFTable) {
            XWPFTable table = (XWPFTable) bodyElement;
            replaceTextInTable(table, replaceData);
        }
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, Map<String, String> replaceData) {
        for (XWPFRun run : paragraph.getRuns()) {
            Log.d("ДАННЫЕ РЕЗЮМЕ","ПРГРФ: "+paragraph.getParagraphText());
            String text = run.getText(0);
            if (text != null) {
                Log.d("ДАННЫЕ РЕЗЮМЕ","его текст: "+text);
                for (Map.Entry<String, String> entry : replaceData.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                run.setText(text, 0);
            }
        }
    }

    private void replaceTextInTable(XWPFTable table, Map<String, String> replaceData) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    replaceTextInParagraph(paragraph, replaceData);
                }

                for (XWPFTable nestedTable : cell.getTables()) {
                    replaceTextInTable(nestedTable, replaceData);
                }
            }
        }
    }

    private void replaceTextInFootnote(XWPFFootnote footnote, Map<String, String> replaceData) {
        for (XWPFParagraph paragraph : footnote.getParagraphs()) {
            replaceTextInParagraph(paragraph, replaceData);
        }
    }

    private void replaceTextInNumbering(XWPFAbstractNum abstractNum, Map<String, String> replaceData) {

    }
}