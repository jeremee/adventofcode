package adventofcode2021.day16

import java.io.File

fun main(vararg args: String) {
    val inputAsHex = File("data").readLines().first()

    val inputAsBits = inputAsHex.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")

    val packets = readPackets(inputAsBits)

    println("sum : ${sumPacketsVersion(packets)}")
}

private fun sumPacketsVersion(packets: List<PacketStar1>): Int =
        packets.sumBy {
            if (it is PacketStar1.WithSubPackets) {
                it.version + sumPacketsVersion(it.subpackets)
            } else {
                it.version
            }
        }

private fun isEnd(input: String) = input.trim('0').isEmpty()

private fun readPackets(bits: String): List<PacketStar1> {
    var i = 0
    val packets = mutableListOf<PacketStar1>()
    while (!isEnd(bits.drop(i))) {
        val packet = readPacket(bits.drop(i))
        packets.add(packet)
        i += packet.size
    }
    return packets
}


private fun readPacket(bits: String): PacketStar1 {
    val version = bits.substring(0..2).toInt(2)
    val packetType = bits.substring(3..5).toInt(2)

    return when {
        packetType == 4 -> readLiteral(bits.drop(6), version)
        bits[6] == '0' -> readOperator0(bits.drop(7), version, packetType)
        bits[6] == '1' -> readOperator1(bits.drop(7), version, packetType)
        else -> throw IllegalStateException("Unknown packet")
    }
}

private fun readOperator0(bits: String, version: Int, type: Int): PacketStar1 {
    val subPacketsLength = bits.take(15).toInt(2)

    val subpackets = readPackets(bits.substring(15..(15 + subPacketsLength - 1)))

    return PacketStar1.PacketOperator0(version, 7 + 15 + subPacketsLength, subpackets)
}

private fun readOperator1(bits: String, version: Int, type: Int): PacketStar1 {
    val subPacketsNumber = bits.take(11).toInt(2)

    val subpackets = mutableListOf<PacketStar1>()
    var i = 0
    (1..subPacketsNumber).forEach {
        val packet = readPacket(bits.drop(11 + i))
        i += packet.size
        subpackets.add(packet)
    }

    return PacketStar1.PacketOperator0(version, 7 + 11 + i, subpackets)
}

private fun readLiteral(bits: String, version: Int): PacketStar1.PacketLiteral {
    var i = 0

    do {
        val isLast = bits[i] == '0'
        i += 5
    } while (!isLast)

    return PacketStar1.PacketLiteral(version, 6 + i)
}

sealed class PacketStar1(open val version: Int, open val size: Int) {
    data class PacketLiteral(override val version: Int,
                             override val size: Int) : PacketStar1(version, size)

    interface WithSubPackets {
        val subpackets: List<PacketStar1>
    }

    data class PacketOperator0(override val version: Int,
                               override val size: Int,
                               override val subpackets: List<PacketStar1>) : PacketStar1(version, size), WithSubPackets
}