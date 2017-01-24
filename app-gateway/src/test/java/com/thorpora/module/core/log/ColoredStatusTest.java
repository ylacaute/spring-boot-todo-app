package com.thorpora.module.core.log;

import com.thorpora.test.junit.TestDecorator;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.thorpora.module.core.log.ColoredStatus.Status.INIT;
import static com.thorpora.module.core.log.ColoredStatus.Status.SUCCESS;

public class ColoredStatusTest {

    private final static Logger log = LoggerFactory.getLogger(ColoredStatusTest.class);

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @Test
    public void ColoredStatus_basicUsedCases_valid() {
        String result = ColoredStatus.getText(SUCCESS, "This is part of a test");
        if (!result.equals("\u001B[1m\u001B[37m[\u001B[32mSUCCESS\u001B[37m] This is part of a test \u001B[0m")
                && !result.equals("[SUCCESS] This is part of a test")) {
            Assertions.assertThat(true);
        }
    }

    @Test
    public void ColoredStatus_nullTexts_valid() {
        log.info(ColoredStatus.getText(INIT, null));
        log.info(ColoredStatus.getText(SUCCESS, null, null));
    }

    @Test
    public void ColoredStatus_emptyTexts_valid() {
        log.info(ColoredStatus.getText(INIT, ""));
        log.info(ColoredStatus.getText(INIT, "", ""));
    }

}