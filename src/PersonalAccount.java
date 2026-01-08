public class PersonalAccount extends Account {

    public PersonalAccount() {
        overdraftEnabled = true;
        overdraftLimit = 100.00;
    }

    protected String getSortCodeForType() {
        return "60-60-60";
    }

}
