rootProject.name = "telegram-bot"

include(
    listOf(
        ":lib:telegram-common",
        ":lib:openai-api",
        ":lib:audioconverter",
    )
)
