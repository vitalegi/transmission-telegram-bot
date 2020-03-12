package it.vitalegi.transmissiontelegrambot.telegram.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestWrapperTest {

	@Test
	void testTokenizerEmptyTextShouldGiveEmptyList() {
		List<String> tokenized = new RequestWrapperImpl("").tokenizeText();
		assertNotNull(tokenized);
		assertEquals(0, tokenized.size());
	}

	@Test
	void testTokenizerSimpleTextShouldGiveOneElement() {
		List<String> tokenized = new RequestWrapperImpl("abc").tokenizeText();
		assertNotNull(tokenized);
		assertEquals(1, tokenized.size());
		assertEquals("abc", tokenized.get(0));
	}

	@Test
	void testTokenizerTwoWordsShouldGiveTwoElements() {
		List<String> tokenized = new RequestWrapperImpl("abc def").tokenizeText();
		assertNotNull(tokenized);
		assertEquals(2, tokenized.size());
		assertEquals("abc", tokenized.get(0));
		assertEquals("def", tokenized.get(1));
	}
}
