package com.thewire.wenlaunch.repository

import android.net.Uri
import com.thewire.wenlaunch.domain.model.*
import java.time.ZonedDateTime

object MockLaunchData {
    val statuses = listOf(
        Status(
            id = 1,
            name = "Go for launch",
            abbrev = LaunchStatus.GO,
            description = "Current T-0 confirmed by official or reliable sources.",
        )
    )

    val Orbits = listOf(
        Orbit(
            id = 0,
            name = "Low Earth Orbit",
            abbrev = "LEO",
        )
    )

    val missions = listOf(
        Mission(
            id = 5959,
            name = "Starlink Group 4-12",
            description = "A batch of 53 satellites for Starlink mega-constellation - SpaceX's project for space-based Internet communication system.",
            orbit = Orbits[0],
        )
    )

    val configurations = listOf(
        RocketConfiguration(
            id = 164,
            name = "Falcon 9",
            family = "Falcon",
            variant = "Block 5",
            fullName = "Falcon 9 Block 5",
        )
    )

    val rockets = listOf(
        Rocket(
            id = 7535,
            configuration = configurations[0],
        )
    )

    val locations = listOf(
        Location(
            id = 12,
            name = "Cape Canaveral, FL, USA",
            mapImage = Uri.parse("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/location_12_20200803142519.jpg")
        )
    )

    val pads = listOf(
        Pad(
            id = 80,
            name = "Space Launch Complex 40",
            location = locations[0],
        )
    )


    val launches = listOf<Launch>(
        Launch(
            id = "72188aca-810d-40b9-887d-43040614dd2c",
            url = Uri.parse("https://ll.thespacedevs.com/2.2.0/launch/72188aca-810d-40b9-887d-43040614dd2c/"),
            name = "Falcon 9 Block 5 | Starlink Group 4-12",
            status = statuses[0],
            net = ZonedDateTime.parse("2022-03-19T04:42:30Z"),
            rocket = rockets[0],
            mission = missions[0],
            pad = pads[0],
            image = Uri.parse("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20220225140920.png"),
            webcastLive = true,
            vidUris = listOf<VidUri>(),
            modifiedAt = 0,
        ),
        Launch(
            id = "663f3c14-f2d5-4547-8a4c-dd5ea5bf35bf",
            url = Uri.parse("https://ll.thespacedevs.com/2.2.0/launch/72188aca-810d-40b9-887d-43040614dd2c/"),
            name = "Soyuz 2.1a/Fregat-M | Meridian-M No.20L",
            status = statuses[0],
            net = ZonedDateTime.parse("2022-03-19T04:42:30Z"),
            rocket = rockets[0],
            mission = missions[0],
            pad = pads[0],
            image = Uri.parse("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20220225140920.png"),
            webcastLive = true,
            vidUris = listOf<VidUri>(),
            modifiedAt = 0,
        ),
        Launch(
            id = "86ea974e-6464-4960-85df-11af88852d57",
            url = Uri.parse("https://ll.thespacedevs.com/2.2.0/launch/72188aca-810d-40b9-887d-43040614dd2c/"),
            name = "Zhuque-2 | Maiden Flight",
            status = statuses[0],
            net = ZonedDateTime.parse("2022-03-19T04:42:30Z"),
            rocket = rockets[0],
            mission = missions[0],
            pad = pads[0],
            image = Uri.parse("https://spacelaunchnow-prod-east.nyc3.digitaloceanspaces.com/media/launch_images/falcon2520925_image_20220225140920.png"),
            webcastLive = true,
            vidUris = listOf<VidUri>(),
            modifiedAt = 0,
        ),

    )
}

