import java.util.Objects;

//класс Пациент
public record Patient(String name, String surName, String birthDate, boolean healthy) implements Comparable<Patient> {

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", birthDate=" + birthDate +
                ", healthy=" + healthy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        return Objects.equals(name(), patient.name()) && Objects.equals(surName(), patient.surName()) && Objects.equals(birthDate(), patient.birthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), surName(), birthDate());
    }

    @Override
    public int compareTo(Patient other) {
        return this.birthDate().compareTo(other.birthDate());
    }
}

