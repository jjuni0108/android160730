package com.example.c.criminalintent.SQLite;

/**
 * Created by c on 2016-08-06.
 */
public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";
        public static final class Cols{
            public static final String UUID ="uuid";
            public static final String TITLE ="title";
            public static final String DATE ="date";
            public static final String SOLVED ="solved";
        }
    }
}
