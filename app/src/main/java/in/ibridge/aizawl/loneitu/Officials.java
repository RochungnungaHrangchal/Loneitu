package in.ibridge.aizawl.loneitu;

public class Officials {
    String designation,name,contact;

    public Officials(String designation,String name,String contact)
    {
        this.designation=designation;
        this.name=name;
        this.contact=contact;

    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
