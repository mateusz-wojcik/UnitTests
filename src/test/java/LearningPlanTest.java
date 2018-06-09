import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
//import static org.testng.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LearningPlanTest {

    private LearningPlan learningPlan;
    private List<Exercise> normalList, emptyList, doneList, zeroList, listWithZero, listWithNull;

    @Mock
    private LearningPlan plan;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init(){
        learningPlan = new LearningPlan();
        initListsToTest();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateGradeStandardInputs() {
        assertAll(
                () -> assertEquals(2, learningPlan.calculateGrade(0.0)),
                () -> assertEquals(2, learningPlan.calculateGrade(0.2)),
                () -> assertEquals(3, learningPlan.calculateGrade(0.5)),
                () -> assertEquals(3, learningPlan.calculateGrade(0.6)),
                () -> assertEquals(4, learningPlan.calculateGrade(0.7)),
                () -> assertEquals(4, learningPlan.calculateGrade(0.8)),
                () -> assertEquals(5, learningPlan.calculateGrade(0.9)),
                () -> assertEquals(5, learningPlan.calculateGrade(1.0))
        );
    }

    @Test
    public void testCalculateGradeNegativeValue() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> {
                        learningPlan.calculateGrade(-1);
                    }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                        learningPlan.calculateGrade(-150);
                    })
        );

    }

    @Test
    public void testCalculateGradeHighValue() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    learningPlan.calculateGrade(12.5);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    learningPlan.calculateGrade(9999);
                })
        );
    }

    @Test
    public void testCalculateDoneExercisePercent() {
        when(plan.calculateDoneExercisesPercent()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(5);
                    when(plan.getExercisesNumber()).thenReturn(10);
                    assertEquals(50, plan.calculateDoneExercisesPercent());
                },
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(1);
                    when(plan.getExercisesNumber()).thenReturn(20);
                    assertEquals(5, plan.calculateDoneExercisesPercent());
                },
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(27);
                    when(plan.getExercisesNumber()).thenReturn(30);
                    assertEquals(90, plan.calculateDoneExercisesPercent());
                }
        );

        verify(plan, atLeast(3)).calculateDoneExercises();
        verify(plan, atLeast(3)).getExercisesNumber();
    }

    @Test
    public void testCalculateDoneExercisePercentBoundaries() {
        when(plan.calculateDoneExercisesPercent()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(0);
                    when(plan.getExercisesNumber()).thenReturn(30);
                    assertEquals(0, plan.calculateDoneExercisesPercent());
                },
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(40);
                    when(plan.getExercisesNumber()).thenReturn(40);
                    assertEquals(100, plan.calculateDoneExercisesPercent());
                }
        );

        verify(plan, times(2)).calculateDoneExercises();
        verify(plan, times(2)).getExercisesNumber();
    }

    @Test
    public void testCalculateDoneExercisePercentIncorrectArguments() {
        when(plan.calculateDoneExercisesPercent()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(-1);
                    when(plan.getExercisesNumber()).thenReturn(10);
                    assertThrows(IllegalArgumentException.class, plan::calculateDoneExercisesPercent);
                },
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(250);
                    when(plan.getExercisesNumber()).thenReturn(10);
                    assertThrows(IllegalArgumentException.class, plan::calculateDoneExercisesPercent);
                },
                () -> {
                    when(plan.calculateDoneExercises()).thenReturn(25);
                    when(plan.getExercisesNumber()).thenReturn(0);
                    assertThrows(ArithmeticException.class, plan::calculateDoneExercisesPercent);
                }
        );

        verify(plan, atLeast(2)).calculateDoneExercises();
        verify(plan, atLeast(2)).getExercisesNumber();
    }

    @Test
    public void testIsDoneExpectedFalse(){
        when(plan.isDone()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(0);
                    assertFalse(plan.isDone());

                },
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(10);
                    assertFalse(plan.isDone());

                },
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(70);
                    assertFalse(plan.isDone());
                }
        );

        verify(plan, atLeast(2)).calculateDoneExercisesPercent();
    }

    @Test
    public void testIsDoneExpectedTrue(){
        when(plan.isDone()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(80);
                    assertTrue(plan.isDone());
                },
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(90);
                    assertTrue(plan.isDone());
                },
                () -> {
                    when(plan.calculateDoneExercisesPercent()).thenReturn(100);
                    assertTrue(plan.isDone());
                }
        );

        verify(plan, atMost(3)).calculateDoneExercisesPercent();
    }


    @Test
    public void testIsDoneExpectedException(){
        when(plan.isDone()).thenCallRealMethod();

        when(plan.calculateDoneExercisesPercent()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, plan::isDone);

        verify(plan, atLeastOnce()).calculateDoneExercisesPercent();
    }

    @Test
    public void testGetAverageGrade(){
        LearningPlan plan = spy(learningPlan); //instead of when(plan.getAverageGrade(arg).thenCallReadMethod())

        assertAll(
                () -> {
                    assertEquals(0.7, plan.getAverageGrade(normalList), 0.01);
                },
                () -> {
                    assertEquals(0.0, plan.getAverageGrade(emptyList), 0.01);
                },
                () -> {
                    assertEquals(1.0, plan.getAverageGrade(doneList), 0.01);
                },
                () -> {
                    assertEquals(0.5, plan.getAverageGrade(listWithZero), 0.01);
                },
                () -> {
                    assertEquals(0.0, plan.getAverageGrade(zeroList), 0.01);
                },
                () -> assertThrows(NullPointerException.class,
                        () -> {
                            plan.getAverageGrade(listWithNull);
                        }),
                () -> assertThrows(NullPointerException.class,
                        () -> {
                            plan.getAverageGrade(null);
                        })
        );
    }

    @Test
    public void testCalculateDoneExercises(){
        when(plan.calculateDoneExercises()).thenCallRealMethod();

        assertAll(
                () -> {
                    when(plan.getExerciseList()).thenReturn(normalList);
                    assertEquals(2, plan.calculateDoneExercises());

                },
                () -> {
                    when(plan.getExerciseList()).thenReturn(emptyList);
                    assertEquals(0, plan.calculateDoneExercises());

                },
                () -> {
                    when(plan.getExerciseList()).thenReturn(doneList);
                    assertEquals(3, plan.calculateDoneExercises());

                },
                () -> {
                    when(plan.getExerciseList()).thenReturn(null);
                    assertThrows(NullPointerException.class,
                            () -> {
                                plan.calculateDoneExercises();
                            });
                },
                () -> {
                    when(plan.getExerciseList()).thenReturn(listWithNull);
                    assertThrows(NullPointerException.class,
                            () -> {
                                plan.calculateDoneExercises();
                            });
                }
        );

        verify(plan, times(5)).calculateDoneExercises();
        verify(plan, times(5)).getExerciseList();
    }


    public void initListsToTest(){
        normalList = Arrays.asList(new Exercise[]{new Writing("Task1"){}, new Writing("Task2"), new Writing("Task3")});
        when(plan.getAverageGrade(normalList)).thenCallRealMethod();
        normalList.get(0).setGrade(0.5);
        normalList.get(1).setGrade(0.7);
        normalList.get(2).setGrade(0.9);

        zeroList = Arrays.asList(new Exercise[]{new Writing("Task1"){}, new Writing("Task2"), new Writing("Task3")});
        when(plan.getAverageGrade(normalList)).thenCallRealMethod();
        zeroList.get(0).setGrade(0.0);
        zeroList.get(1).setGrade(0.0);
        zeroList.get(2).setGrade(0.0);

        listWithZero = Arrays.asList(new Exercise[]{new Writing("Task1"){}, new Writing("Task2"), new Writing("Task3")});
        when(plan.getAverageGrade(normalList)).thenCallRealMethod();
        listWithZero.get(0).setGrade(0.5);
        listWithZero.get(1).setGrade(0.0);
        listWithZero.get(2).setGrade(1.0);

        listWithNull = Arrays.asList(new Exercise[]{new Writing("Task1"){}, null, new Writing("Task3")});
        when(plan.getAverageGrade(normalList)).thenCallRealMethod();
        listWithNull.get(0).setGrade(0.5);
        listWithNull.get(2).setGrade(1.0);

        doneList = Arrays.asList(new Exercise[]{new Writing("Task1"){}, new Writing("Task2"), new Writing("Task3")});
        when(plan.getAverageGrade(doneList)).thenCallRealMethod();
        doneList.get(0).setGrade(1);
        doneList.get(1).setGrade(1);
        doneList.get(2).setGrade(1);

        emptyList = new ArrayList<>();

    }

}