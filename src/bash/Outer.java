package bash;

public class Outer {
    private int x=3;
    class Inner{
        public void foo() {
            Outer.this.x++;
            System.out.println(x);
        }
    }
    public static void main(String[] args) {
        Outer name = new Outer();
        Outer.Inner name1 = name.new Inner();
        name1.foo();
        System.out.println(name.x);
    }
}
