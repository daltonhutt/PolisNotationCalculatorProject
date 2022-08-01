package com.ust.reversepolisnotationcalculator;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@SpringBootApplication
public class ReversePolishNotationCalculatorApplication {
    public static void main(String[] args) {
        //SpringApplication.run(ReversePolishNotationCalculatorApplication.class, args);
        System.out.println(findAnswer("2 1 - 8 +"));
        System.out.println(findAnswer("5 1 2 + 4 * + 3 -"));

    }

    interface Calculator {
        int findAnswer(int a, int b);
    }

    enum PolishCalculation implements Calculator {
        ADD('+') {
            @Override
            public int findAnswer(int a, int b) {
                return a + b;
            }
        },
        SUB('-') {
            @Override
            public int findAnswer(int a, int b) {
                return a - b;
            }
        },
        MUL('*') {
            @Override
            public int findAnswer(int a, int b) {
                return a * b;
            }
        },
        DIV('/') {
            @Override
            public int findAnswer(int a, int b) {
                return a / b;
            }
        };

        private final char op;
        private PolishCalculation(char c) {
            this.op = c;
        }

        public static PolishCalculation getByOp(final char op) {
            for (final PolishCalculation math : values() ) {
                if (math.op == op) return math;
            }
            return null;
        }
    }

    final static List<String> OPS = Arrays.asList("+", "-", "*", "/");

    private static int findAnswer(final String input) {
        final String[] elements = input.split(" ");
        final Stack<Integer> stack = new Stack<>();
        for (int index = 0; index < elements.length; index++ ) {
            final String current = elements[index];
            if ( OPS.contains(current) ) {
                // calc
                final int right = stack.pop();
                final int left = stack.pop();
                final PolishCalculation calc = PolishCalculation.getByOp(current.charAt(0));
                final int result = calc.findAnswer(left, right);
                stack.push(result);
            } else {
                stack.push(Integer.valueOf(current));
            }
        }
        return stack.pop();
    }

}
