import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//import static org.junit.Assert.*;

public class WritingTest {

    private Writing writingOneArgument, writingTwoArguments, writingNullArgument;

    @Mock
    private
    Writing writingExercise;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        writingOneArgument = new Writing("Sample task.");
        writingTwoArguments = new Writing("sample task", new String[]{"computer, Java, programming"});
        writingNullArgument = new Writing(null, null);
    }

    @Test
    public void testGetUsefulWordsEqual() {
        assertThat(new String[]{"computer, Java, programming"}, is(writingTwoArguments.getUsefulWords()));
        assertArrayEquals(new String[]{"computer, Java, programming"}, writingTwoArguments.getUsefulWords());
    }

    @Test //przechodzi, bo porownujemy referencje
    public void testUsefulWordsArraysAreTheSame(){
        String[] usefulWords = {"computer, Java, programming"};
        writingTwoArguments.setUsefulWords(usefulWords);
        assertSame(usefulWords, writingTwoArguments.getUsefulWords());
    }

    @Test
    public void testTaskNotNull(){
        assertNotNull(writingOneArgument.getTask());
    }

    @Test
    public void testUsefulWordsIsNull(){
        assertNull(writingOneArgument.getUsefulWords());
    }

    @Test
    public void testTaskIsCorrectDefined(){
        assertAll(
                () -> {
                    assertNotNull(writingOneArgument.getTask());

                    assertAll(
                            () -> assertFalse(writingOneArgument.getTask().isEmpty()),
                            () -> assertTrue(Character.isUpperCase(writingOneArgument.getTask().charAt(0))),
                            () -> assertEquals('.', writingOneArgument.getTask().charAt(writingOneArgument.getTask().length() - 1))
                    );
                },
                () -> {
                    assertNull(writingNullArgument.getTask());
                },
                () -> {
                    assertNotNull(writingTwoArguments.getTask());

                    assertAll(
                            () -> assertFalse(writingTwoArguments.getTask().isEmpty()),
                            () -> assertFalse(Character.isUpperCase(writingTwoArguments.getTask().charAt(0))),
                            () -> assertNotEquals('.', writingTwoArguments.getTask().charAt(writingTwoArguments.getTask().length() - 1))
                    );
                }
        );
    }

    @Test
    public void testCalculateGradePercent(){
        when(writingExercise.calculateGradePercent()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"b", "a", "a", "a"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"a", "d", "c", "d"});
                    assertEquals(0, writingExercise.calculateGradePercent());
                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"a", "b", "d", "d"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"a", "d", "c", "d"});
                    assertEquals(50, writingExercise.calculateGradePercent());
                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"one", "two", "four", "four"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"one", "two", "three", "four"});
                    assertEquals(75, writingExercise.calculateGradePercent());
                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"a", "d", "c", "d"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"a", "d", "c", "d"});
                    assertEquals(100, writingExercise.calculateGradePercent());
                    assertArrayEquals(new String[]{"a", "d", "c", "d"}, writingExercise.getUserAnswers());
                    assertArrayEquals(new String[]{"a", "d", "c", "d"}, writingExercise.getCorrectAnswers());
                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{});
                    assertThrows(ArithmeticException.class,
                            () -> writingExercise.calculateGradePercent());
                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(null);
                    when(writingExercise.getCorrectAnswers()).thenReturn(null);
                    assertThrows(NullPointerException.class,
                            () -> writingExercise.calculateGradePercent());
                }
        );
    }

    @Test
    public void testCalculateGradePercentDifferentArrayLength(){
        when(writingExercise.calculateGradePercent()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"b", "a", "a"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"a", "d", "c", "d"});

                    assertNotEquals(writingExercise.getCorrectAnswers(), writingExercise.getUserAnswers().length);

                    assertAll(
                            () -> assertThrows(ArrayIndexOutOfBoundsException.class,
                                    () -> writingExercise.calculateGradePercent())
                    );

                },
                () -> {
                    when(writingExercise.getUserAnswers()).thenReturn(new String[]{"a", "b", "d", "d"});
                    when(writingExercise.getCorrectAnswers()).thenReturn(new String[]{"a", "d", "c"});

                    assertNotEquals(writingExercise.getCorrectAnswers(), writingExercise.getUserAnswers().length);

                    assertAll(
                            () -> assertThrows(UnsupportedOperationException.class,
                                    () -> writingExercise.calculateGradePercent())
                    );
                }
        );

        verify(writingExercise, times(11)).getCorrectAnswers();
        verify(writingExercise, times(8)).getUserAnswers();
    }

    @Test(timeout = 1000) //przerywa
    public void testSendAnswerRespond(){
        writingOneArgument.sendAnswer("answer");
    }

    @Test //nie przerywa
    public void testSendAnswerTimeout(){
        assertTimeout(Duration.ofMillis(1000), () -> {
            writingOneArgument.sendAnswer("answer");
        });
    }

    @Test //przerywa
    public void testSendAnswerTimeoutPreemptively(){
        assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
            writingOneArgument.sendAnswer("answer");
        });
    }

}