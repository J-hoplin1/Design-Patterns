class Shape{
    private int width;

    Shape(int width){
        this.width = width;
    }
    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int returnWidth(){
        return getWidth();
    }
}

class Square extends Shape{
    Square(int width){
        super(width);
    }

    @Override
    public int returnWidth() {
        return super.returnWidth();
    }
}

class Circle extends Shape{
    Circle(int width){
        super(width);
    }

    @Override
    public int returnWidth() {
        return super.returnWidth();
    }
}

class Triangle extends Shape{
    Triangle(int width){
        super(width);
    }

    @Override
    public int returnWidth() {
        return super.returnWidth();
    }
}
