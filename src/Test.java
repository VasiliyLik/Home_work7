import java.io.IOException;

//реализация программы
public class Test {
    public static void main(String[] args) throws IOException {
        PatientBase patientBase = new PatientBase();
        patientBase.createPatient(1);
        patientBase.addPatientFromNetFile();
        patientBase.transferFromScanToFinalFile();
        patientBase.transferFromNetFileToFinalFile();
        patientBase.readFinalFile();
    }
}
