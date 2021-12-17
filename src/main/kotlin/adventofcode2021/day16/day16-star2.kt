package adventofcode2021.day16

import java.io.File

fun main(vararg args: String) {
    val inputAsHex = File("data").readLines().first()

    val inputAsBits = inputAsHex.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")

    val packet = readPacket(inputAsBits)

    println("result: ${packet.value}")
}

private fun sumPacketsVersion(packets: List<Packet>): Int =
        packets.sumBy {
            if (it is Packet.WithSubPackets) {
                it.version + sumPacketsVersion(it.subpackets)
            } else {
                it.version
            }
        }

private fun isEnd(input: String) = input.trim('0').isEmpty()

private fun readPackets(bits: String): List<Packet> {
    var i = 0
    val packets = mutableListOf<Packet>()
    while (!isEnd(bits.drop(i))) {
        val packet = readPacket(bits.drop(i))
        packets.add(packet)
        i += packet.size
    }
    return packets
}

private fun readPacket(bits: String): Packet {
    val version = bits.substring(0..2).toInt(2)
    val packetType = bits.substring(3..5).toInt(2)

    return when {
        packetType == 4 -> readLiteral(bits.drop(6), version)
        bits[6] == '0' -> readOperator0(bits.drop(7), version, packetType)
        bits[6] == '1' -> readOperator1(bits.drop(7), version, packetType)
        else -> throw IllegalStateException("Unknown packet")

    }
}

private fun readOperator0(bits: String, version: Int, type: Int): Packet {
    val subPacketsLength = bits.take(15).toInt(2)

    val subpackets = readPackets(bits.substring(15..(15 + subPacketsLength - 1)))

    return Packet.createWithSubPackets(version, type, 7 + 15 + subPacketsLength, subpackets)
}

private fun readOperator1(bits: String, version: Int, type: Int): Packet {
    val subPacketsNumber = bits.take(11).toInt(2)

    val subpackets = mutableListOf<Packet>()
    var i = 0
    (1..subPacketsNumber).forEach {
        val packet = readPacket(bits.drop(11 + i))
        i += packet.size
        subpackets.add(packet)
    }

    return Packet.createWithSubPackets(version, type, 7 + 11 + i, subpackets)
}

private fun readLiteral(bits: String, version: Int): Packet.PacketLiteral {
    var binaryBits = ""
    var i = 0

    do {
        val isLast = bits[i] == '0'
        binaryBits += bits.drop(i).substring(1..4)
        i += 5
    } while (!isLast)

    return Packet.PacketLiteral(version, 6 + i, binaryBits.toLong(2))
}

private sealed class Packet(open val version: Int, open val size: Int, open val value: Long) {
    data class PacketLiteral(override val version: Int,
                             override val size: Int,
                             override val value: Long)
        : Packet(version, size, value)

    interface WithSubPackets {
        val subpackets: List<Packet>
    }

    companion object {
        fun createWithSubPackets(version: Int, type: Int, size: Int, subpackets: List<Packet>): Packet =
                when (type) {
                    0 -> PacketSum(version, size, subpackets)
                    1 -> PacketProduct(version, size, subpackets)
                    2 -> PacketMin(version, size, subpackets)
                    3 -> PacketMax(version, size, subpackets)
                    5 -> PacketGreaterThan(version, size, subpackets)
                    6 -> PacketLessThan(version, size, subpackets)
                    7 -> PacketEquals(version, size, subpackets)
                    else -> throw IllegalStateException("Unknown type $type")
                }
    }

    data class PacketSum(override val version: Int,
                         override val size: Int,
                         override val subpackets: List<Packet>)
        : Packet(version, size, subpackets.map { it.value }.sum()), WithSubPackets

    data class PacketProduct(override val version: Int,
                             override val size: Int,
                             override val subpackets: List<Packet>)
        : Packet(version, size, subpackets.map { it.value }.reduce { acc, l -> acc * l }), WithSubPackets

    data class PacketMin(override val version: Int,
                         override val size: Int,
                         override val subpackets: List<Packet>)
        : Packet(version, size, subpackets.map { it.value }.min()!!), WithSubPackets

    data class PacketMax(override val version: Int,
                         override val size: Int,
                         override val subpackets: List<Packet>)
        : Packet(version, size, subpackets.map { it.value }.max()!!), WithSubPackets

    data class PacketGreaterThan(override val version: Int,
                                 override val size: Int,
                                 override val subpackets: List<Packet>)
        : Packet(version, size, if (subpackets[0].value > subpackets[1].value) 1 else 0), WithSubPackets

    data class PacketLessThan(override val version: Int,
                              override val size: Int,
                              override val subpackets: List<Packet>)
        : Packet(version, size, if (subpackets[0].value < subpackets[1].value) 1 else 0), WithSubPackets

    data class PacketEquals(override val version: Int,
                            override val size: Int,
                            override val subpackets: List<Packet>)
        : Packet(version, size, if (subpackets[0].value == subpackets[1].value) 1 else 0), WithSubPackets
}

