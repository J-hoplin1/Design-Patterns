interface InterfaceE{
    public abstract String operA(String param1);
}


class ClassA{
    public int attr1;

}

class ClassB extends ClassA{

}

class ClassC extends ClassA implements InterfaceE{
    @Override
    public String operA(String param1) {
        return null;
    }
}
