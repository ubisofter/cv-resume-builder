package app.cvresume.android.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.cvresume.android.R;

public class PreviewFragment extends Fragment {

    private View view;
    int position;
    String name, surname, secondName, email, phoneNum, resumeTitle, busy, salary, schedule;
    String city, birthDate, famStatus, citizenship, eduGrade, personalQualities, profSkills, about;
    Boolean children, gender, army, medBook, driveCat;

    String b2sChildren, b2sGender, b2sArmy, b2sMedBook, b2sDriveCat;
    String formettedEx, formettedEd, formettedCo, formettedLa, formettedSk;

    private File filePath = null;

    public static PreviewFragment newInstance(Bundle args) {
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview, container, false);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {

                getData();
                filePath = new File(requireContext().getExternalFilesDir(Context.DOWNLOAD_SERVICE), "Test.docx");

                handler.post(() -> {
                    try {
                        changeValues();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });

        return view;
    }

    private void getData() {
        Bundle args = getArguments();
        if (args != null) {
            position = args.getInt("templatePosition");
            surname = args.getString("surname");
            name = args.getString("name");
            secondName = args.getString("secondName");
            email = args.getString("email");
            phoneNum = args.getString("phoneNum");
            resumeTitle = args.getString("resumeTitle");
            busy = args.getString("busy");
            salary = args.getString("salary");
            schedule = args.getString("schedule");

            city = args.getString("city");
            birthDate = args.getString("birthDate");
            famStatus = args.getString("famStatus");
            children = args.getBoolean("children");
            citizenship = args.getString("citizenship");
            gender = args.getBoolean("gender");
            eduGrade = args.getString("eduGrade");
            army = args.getBoolean("army");
            driveCat = args.getBoolean("driveCat");
            personalQualities = args.getString("personalQualities");
            profSkills = args.getString("profSkills");
            medBook = args.getBoolean("medBook");
            about = args.getString("about");

            if (children) {
                b2sChildren = "Да";
            } else {
                b2sChildren = "Нет";
            }
            if (gender) {
                b2sGender = "Женский";
            } else {
                b2sGender = "Мужской";
            }
            if (army) {
                b2sArmy = "Служил";
            } else {
                b2sArmy = "Не служил";
            }
            if (driveCat) {
                b2sDriveCat = "Есть";
            } else {
                b2sDriveCat = "Нет";
            }
            if (medBook) {
                b2sMedBook = "Есть";
            } else {
                b2sMedBook = "Нет";
            }

            ArrayList<String> experienceDataList = args.getStringArrayList("experienceDataList");
            ArrayList<String> educationDataList = args.getStringArrayList("educationDataList");
            ArrayList<String> courseDataList = args.getStringArrayList("courseDataList");
            ArrayList<String> langDataList = args.getStringArrayList("langDataList");
            ArrayList<String> skillDataList = args.getStringArrayList("skillDataList");

            formettedEx = makeExString(experienceDataList).toString();
            formettedEd = makeEdString(educationDataList).toString();
            formettedCo = makeCoString(courseDataList).toString();
            formettedLa = makeLaString(langDataList).toString();
            formettedSk = makeSkString(skillDataList).toString();

            Log.d("position", "position = " + position);
            Log.d("name", "name = " + name);
            Log.d("children", "children = " + children);
            Log.d("experienceDataList", "experienceDataList = " + skillDataList);

            if (view != null) {
                TextView posTV = view.findViewById(R.id.pPosTV);
                posTV.setText("Position: " + position);
                TextView nameTV = view.findViewById(R.id.pNameTV);
                nameTV.setText("Name: " + name);
                TextView childTV = view.findViewById(R.id.pChildTV);
                childTV.setText("Has children? - " + children);

                TextView experienceTV = view.findViewById(R.id.pExperienceTV);
                experienceTV.setText("Skills: " + "\n\n" + formettedSk);
            }
        }
    }

    public StringBuilder makeExString(ArrayList<String> experienceDataList){
        ArrayList<String> formattedList = new ArrayList<>();

        for (String input : experienceDataList) {
            String[] parts = input.split("\n");
            if (parts.length >= 6) {
                String formattedText = "• " + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4] + "\n" + parts[5];
                formattedList.add(formattedText);
            }
        }

        StringBuilder formattedData = new StringBuilder();

        for (String formattedText : formattedList) {
            formattedData.append(formattedText).append("\n\n");
        }

        Log.d("formattedText", formattedData.toString());
        return formattedData;
    }

    public StringBuilder makeEdString(ArrayList<String> educationDataList){
        ArrayList<String> formattedList = new ArrayList<>();

        for (String input : educationDataList) {
            String[] parts = input.split("\n");
            if (parts.length >= 5) {
                String formattedText = "• " + parts[1] + "\n" + parts[2] + "\n" + parts[4] + "\n" + parts[5];
                formattedList.add(formattedText);
            }
        }

        StringBuilder formattedData = new StringBuilder();

        for (String formattedText : formattedList) {
            formattedData.append(formattedText).append("\n\n");
        }

        Log.d("formattedText", formattedData.toString());
        return formattedData;
    }

    public StringBuilder makeCoString(ArrayList<String> courseDataList){
        ArrayList<String> formattedList = new ArrayList<>();

        for (String input : courseDataList) {
            String[] parts = input.split("\n");
            if (parts.length >= 4) {
                String formattedText = "• " + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n" + parts[4];
                formattedList.add(formattedText);
            }
        }

        StringBuilder formattedData = new StringBuilder();

        for (String formattedText : formattedList) {
            formattedData.append(formattedText).append("\n\n");
        }

        Log.d("formattedText", formattedData.toString());
        return formattedData;
    }

    public StringBuilder makeLaString(ArrayList<String> langDataList){
        ArrayList<String> formattedList = new ArrayList<>();

        for (String input : langDataList) {
            String[] parts = input.split("\n");
            if (parts.length >= 3) {
                String formattedText = "• "
                        + parts[1] + " (Уровень: "
                        + parts[2]
                        + ")\n"
                        + parts[3];
                formattedList.add(formattedText);
            }
        }

        StringBuilder formattedData = new StringBuilder();

        for (String formattedText : formattedList) {
            formattedData.append(formattedText).append("\n\n");
        }

        Log.d("formattedText", formattedData.toString());
        return formattedData;
    }

    public StringBuilder makeSkString(ArrayList<String> skillDataList){
        ArrayList<String> formattedList = new ArrayList<>();

        for (String input : skillDataList) {
            String[] parts = input.split("\n");
            if (parts.length >= 3) {
                String formattedText = "• "
                        + parts[1] + " (Уровень: "
                        + parts[2]
                        + ")\n"
                        + parts[3];
                formattedList.add(formattedText);
            }
        }

        StringBuilder formattedData = new StringBuilder();

        for (String formattedText : formattedList) {
            formattedData.append(formattedText).append("\n\n");
        }

        Log.d("formattedText", formattedData.toString());
        return formattedData;
    }

    public void changeValues() throws IOException {
        try {
            Context context = requireContext();
            Resources resources = context.getResources();
            InputStream templateStream = resources.openRawResource(R.raw.template_1);
            XWPFDocument xwpfDocument = new XWPFDocument(templateStream);

            Map<String, String> replaceData = new HashMap<>();
            replaceData.put("city", city);
            replaceData.put("name", name);
            replaceData.put("surame", surname);
            replaceData.put("second", secondName);
            replaceData.put("email", email);
            replaceData.put("phone", phoneNum);
            replaceData.put("owjd", resumeTitle);
            replaceData.put("busy", busy);
            replaceData.put("salary", salary);
            replaceData.put("schedule", schedule);

            replaceData.put("irom", birthDate);
            replaceData.put("famus", famStatus);
            replaceData.put("chil", b2sChildren);
            replaceData.put("citizenship", citizenship);
            replaceData.put("gender", b2sGender);
            replaceData.put("wpruf", eduGrade);
            replaceData.put("army", b2sArmy);
            replaceData.put("lysm", b2sDriveCat);
            replaceData.put("medook", b2sMedBook);
            replaceData.put("voka", personalQualities);
            replaceData.put("profs", profSkills);
            replaceData.put("about", about);

            replaceData.put("exField", formettedEx);
            replaceData.put("edField", formettedEd);
            replaceData.put("jetlifs", formettedCo);
            replaceData.put("laField", formettedLa);
            replaceData.put("skField", formettedSk);

            Log.d("REPLACE DATA ARRAY", replaceData.values().toString());

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
                Log.d("ДАННЫЕ РЕЗЮМЕ", "\n Данные сохранены ");
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
                Log.d("Текст до замены","ДО ЗАМЕНЫ: "+text);
                for (Map.Entry<String, String> entry : replaceData.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                    Log.d("Текст после замены","ПОСЛЕ ЗАМЕНЫ: "+text);
                }
                // Заменяем переносы строк внутри XWPFRun
                run.setText("", 0); // Очищаем текст
                String[] lines = text.split("\n");
                for (int i = 0; i < lines.length; i++) {
                    run.setText(lines[i], i); // Устанавливаем строки с переносами
                    if (i < lines.length - 1) {
                        run.addCarriageReturn(); // Добавляем перенос строки между строками
                    }
                }
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







