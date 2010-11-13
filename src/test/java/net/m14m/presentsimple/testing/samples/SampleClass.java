package net.m14m.presentsimple.testing.samples;

public class SampleClass {
    @Logged public String contactServer() { return "contactServer()"; }

    @Transactional public String saveChanges() { return "saveChanges()"; }

    @Logged @Transactional public String clearDatabase() { return "clearDatabase()"; }

    public String saveAndClear() {
        return saveChanges() + " " + clearDatabase();
    }

    public String sayHello() { return "sayHello()"; }
}
