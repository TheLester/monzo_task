package com.monzo.androidtest.api.deserializer

import com.google.gson.*
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type


class InstantConverter : JsonSerializer<Instant>, JsonDeserializer<Instant> {

    override fun serialize(src: Instant, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Instant {
        return FORMATTER.parse(json.asString, Instant.FROM)
    }

    companion object {

        private val FORMATTER = DateTimeFormatter.ISO_INSTANT
    }
}