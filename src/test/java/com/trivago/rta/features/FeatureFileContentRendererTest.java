package com.trivago.rta.features;

import com.trivago.rta.vo.DataTable;
import com.trivago.rta.vo.SingleScenario;
import com.trivago.rta.vo.Step;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeatureFileContentRendererTest {

    private FeatureFileContentRenderer featureFileContentRenderer;

    @Before
    public void setup() {
        featureFileContentRenderer = new FeatureFileContentRenderer();
    }

    @Test
    public void getRenderedFeatureFileContentTest() {
        String expectedOutput = "@featureTag1\n" +
                "@featureTag2\n" +
                "Feature: featureName\n" +
                "\n" +
                "@scenarioTag1\n" +
                "@scenarioTag2\n" +
                "Scenario: scenarioName\n" +
                "\n" +
                "Step 1\n" +
                "Step 2\n" +
                "\n" +
                "# Generated by Cucable\n";

        String featureName = "featureName";
        List<String> featureTags = Arrays.asList("@featureTag1", "@featureTag2");
        String scenarioName = "scenarioName";
        List<Step> backgroundSteps = Arrays.asList(
                new Step("Step 1", null),
                new Step("Step 2", null)
        );
        List<String> scenarioTags = Arrays.asList("@scenarioTag1", "@scenarioTag2");

        SingleScenario singleScenario = new SingleScenario(featureName, scenarioName, featureTags, backgroundSteps);
        singleScenario.setScenarioTags(scenarioTags);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }

    @Test
    public void formatDataTableStringTest() {
        String expectedOutput = "Feature: featureName\n" +
                "\n" +
                "Scenario: scenarioName\n" +
                "\n" +
                "Step 1\n" +
                "|cell11|cell12|cell13|\n" +
                "|cell21|cell22|cell23|\n" +
                "\n" +
                "# Generated by Cucable\n";

        String featureName = "featureName";
        String scenarioName = "scenarioName";

        DataTable dataTable = new DataTable();
        dataTable.addRow(Arrays.asList("cell11", "cell12", "cell13"));
        dataTable.addRow(Arrays.asList("cell21", "cell22", "cell23"));

        List<Step> steps = Collections.singletonList(new Step("Step 1", dataTable));

        SingleScenario singleScenario = new SingleScenario(featureName, scenarioName, new ArrayList<>(), new ArrayList<>());
        singleScenario.setSteps(steps);

        String renderedFeatureFileContent = featureFileContentRenderer.getRenderedFeatureFileContent(singleScenario);

        assertThat(renderedFeatureFileContent, is(expectedOutput));
    }
}