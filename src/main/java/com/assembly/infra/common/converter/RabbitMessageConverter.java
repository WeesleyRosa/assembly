/*package com.assembly.infra.common.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class RabbitMessageConverter implements MessageConverter {

    @Value("${spring.rabbitmq.username}")
    private String userId;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) {
        setMessagePropertiesDefault(messageProperties);

        return new Message(objectMapper.writeValueAsBytes(object), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return new Jackson2JsonMessageConverter().fromMessage(message);
    }

    private void setMessagePropertiesDefault(MessageProperties messageProperties) {
        messageProperties.setContentType(MediaType.APPLICATION_JSON_VALUE);
        messageProperties.setMessageId(UUID.randomUUID().toString());
        messageProperties.setTimestamp(new Date());
        messageProperties.setUserId(userId);
    }

}
*/