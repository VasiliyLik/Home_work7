import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

//класс для создания пациентов
public class PatientBase {
    private Patient[] base;
    private Patient[] base2;

    public void createPatient(int count) {
        base = new Patient[count];
        for (int i = 0; i < base.length; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input name:");
            final String name = scanner.nextLine();
            System.out.println("Input surName:");
            final String surName = scanner.nextLine();
            System.out.println("Input birth date:");
            final String birthDate = (scanner.nextLine());
            System.out.println("Input state of health - true or false:");
            boolean healthy = scanner.nextBoolean();

            base[i] = new Patient(name, surName, birthDate, healthy);
        }
        TreeSet<Patient> patientTreeSet = new TreeSet<>();
        Collections.addAll(patientTreeSet, base);
        System.out.println(patientTreeSet);
        base = patientTreeSet.toArray(new Patient[0]);
    }

    public void addPatientFromNetFile() throws IOException {
        String urlAddress = ("https://raw.githubusercontent.com/VasiliyLik/Home_work7/main/patientBase.txt");
        InputStream inputStream = null;
        FileOutputStream foS = null;
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection myUrlCon = (HttpURLConnection) url.openConnection();
            int responseCode = myUrlCon.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = myUrlCon.getInputStream();

                File file = new File("patientLocalFile.txt");
                foS = new FileOutputStream(file);

                int byteRead;
                byte[] buffer = new byte[256];
                while ((byteRead = inputStream.read(buffer)) != -1) {
                    foS.write(buffer, 0, byteRead);
                }
            }
        } catch (IOException ex) {
            System.out.println("Internet connection error " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (foS != null) {
                    foS.close();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        //преобразовываем данные из локального файла в массив
        ArrayList<String> arrayList = new ArrayList<>();
        String line;
        String delim = ";";
        File file = new File("patientLocalFile.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, delim, true);
            boolean expectDelim = false;
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (delim.equals(token)) {
                    if (expectDelim) {
                        expectDelim = false;
                        continue;
                    } else {
                        token = "";
                    }
                }
                expectDelim = true;
                arrayList.add(token);
            }
        }
        String path = "patientLocalFile.txt";
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scan = new Scanner(new File(path))) {
            while (scan.hasNextLine()) {
                stringBuilder.append(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File [%s] is not found.\n", path);
        }
        String[] array = stringBuilder.toString().split(";");
        //System.out.println(Arrays.toString(array));

        int parametrCount = 4;
        base2 = new Patient[array.length / parametrCount];
        for (int i = 0; i < array.length / parametrCount; i++) {
            int cursor = i * parametrCount;
            base2[i] = new Patient(array[cursor], array[cursor + 1], array[cursor + 2], Boolean.parseBoolean(array[cursor + 3]));
        }
        TreeSet<Patient> patientTreeSet2 = new TreeSet<>();
        Collections.addAll(patientTreeSet2, base2);
        base2 = patientTreeSet2.toArray(new Patient[0]);
    }

    //переносим созданный Сканером массив в конечный файл
    public void transferFromScanToFinalFile() {
        File file = new File("finalFile.txt");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            for (Patient patient : base) {
                dos.writeUTF(patient.name());
                dos.writeUTF(patient.surName());
                dos.writeUTF(patient.birthDate());
                dos.writeBoolean(patient.healthy());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //переносим из временного файла в конечный с помощью DataoutputStream
    public void transferFromNetFileToFinalFile() {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("finalFile.txt", true));
            for (Patient patient : base2) {
                dos.writeUTF(patient.name());
                dos.writeUTF(patient.surName());
                dos.writeUTF(patient.birthDate());
                dos.writeBoolean(patient.healthy());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //читаем файл с помощью FileInputSream
    public void readFinalFile() {
        try (FileInputStream fin = new FileInputStream("finalFile.txt")) {
            int i;
            while ((i = fin.read()) != -1) {
                System.out.print((char) i);
                System.out.print(" ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


