
public class Complex {
	
	//Complex number of the form a+bi
	
	public double re;
	public double im;
	
	//often used complex numbers
	public static final Complex i = new Complex(0, 1);
	public static final Complex iNeg = new Complex(0, -1);
	public static final Complex one = new Complex(1, 0);
	public static final Complex oneNeg = new Complex(-1, 0);
	
	private double modulus;
	private double argument;
	
	public Complex(double real, double imaginary) {
		this.re = real;
		this.im = imaginary;
		this.modulus = getModulus();
		this.argument = getArgument();
	}
	
	public static Complex fromPolar(double modulus, double argument) {
		double re = modulus * Math.cos(Math.toRadians(argument));
		double im = modulus * Math.sin(Math.toRadians(argument));
		return new Complex(re, im);
	}
	
	public static Complex fromAngle(double argument) {
		return new Complex(Math.cos(Math.toRadians(argument)), Math.sin(Math.toRadians(argument)));
	}
	
	public double re() {
		return re;
	}
	
	public double im() {
		return im;
	}
	
	public double getModulus() {
		return Math.sqrt(re * re + im * im);	
	}
	
	public double getArgument() {
		return Math.toDegrees(Math.atan2(im, re));
	}
	
	public void rotate(double angle) {
		this.argument = Math.toRadians(this.getArgument() + angle);
		re = this.modulus * Math.cos(this.argument);
		im = this.modulus * Math.sin(this.argument);
	}
	
	public Complex plus(Complex b) {
		double real = re + b.re;
		double imag = im + b.im;
		return new Complex(real, imag);
	}
	public Complex plus(double re, double im) {
		return new Complex(re + this.re, im + this.im);
	}
	
	public Complex times(double scale) {
		return new Complex(re * scale, im * scale);
	}
	
	public Complex times(Complex num) {
		double real = this.re * num.re - this.im * num.im;
		double imag = this.im * num.re + this.re * num.im;
		return new Complex(real, imag);
	}
	
	public Complex conjugate() {
		return new Complex(this.re, this.im);
	}
	
	public Complex dividedBy(Complex num) {
		double factor = 1/(num.re * num.re + num.im * num.im);
		return this.times(factor).times(num.conjugate());
	}
	
	public Complex squared() {
		double real = this.re * this.re - this.im * this.im;
		double imag = 2 * this.re * this.im;
		return new Complex(real, imag);
	}
	
	public Complex pow(double power) {
		double newArg = power * this.argument;
		double newMod = Math.pow(this.modulus, power);
		return fromPolar(newMod, newArg);
	}
	
	public static Complex ln(Complex num) {
		double re = 0.5 * Math.log(num.re * num.re + num.im * num.im);
		double im = Math.atan2(num.im, num.re);
		return new Complex(re, im);
	}
	
	public static Complex sin(Complex num) {
		double re = Math.sin(num.re) * Math.cosh(num.im);
		double im = Math.sinh(num.im) * Math.cos(num.re);
		return new Complex(re, im);
	}
	
	public static Complex cos(Complex num) {
		double re = Math.cos(num.re) * Math.cosh(num.im);
		double im = -Math.sin(num.re) * Math.sinh(num.im);
		return new Complex (re, im);
	}
	
	public static Complex tan(Complex num) {
		return Complex.sin(num).dividedBy(Complex.cos(num));
	}
	
	public static Complex csc(Complex num) {
		return Complex.one.dividedBy(Complex.sin(num));
	}
	
	public static Complex sec(Complex num) {
		return Complex.one.dividedBy(Complex.cos(num));
	}
	
	public static Complex cot(Complex num) {
		return Complex.one.dividedBy(Complex.tan(num));
	}
	
	public static Complex sinh(Complex num) {
		return Complex.sin(i.times(num)).dividedBy(i);
	}
	
	public static Complex cosh(Complex num) {
		return Complex.cos(i.times(num));
	}
	
	public static Complex tanh(Complex num) {
		return Complex.tan(i.times(num)).dividedBy(i);
	}
	
	public static Complex csch(Complex num) {
		return Complex.one.dividedBy(Complex.sinh(num));
	}
	
	public static Complex sech(Complex num) {
		return Complex.one.dividedBy(Complex.cosh(num));
	}
	
	public static Complex coth(Complex num) {
		return Complex.one.dividedBy(Complex.tanh(num));
	}
	
	public static Complex sum (Complex a, Complex b) {
		return new Complex(a.re + b.re, a.im + b.im);
	}
	
	public static Complex exp(Complex num) {
		double re = Math.exp(num.re) * Math.cos(num.im);
		double im = Math.exp(num.re) * Math.sin(num.im);
		return new Complex(re, im);
	}
	
	public void print() {
		long precision = 100000;
		double tRe = (int)(this.re * precision) / precision;
		double tIm = (int)(this.im * precision) / precision;
		double tMod = (int)(this.getModulus() * precision) / precision;
		double tArg = (int)(this.getArgument() * precision) / precision;
		
		System.out.println( tRe + " + " + tIm + "i");
		System.out.println("modulus: " + tMod);
		System.out.println("argument: " + tArg);
	}
}
