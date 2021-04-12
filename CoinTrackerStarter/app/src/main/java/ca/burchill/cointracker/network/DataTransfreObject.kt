package ca.burchill.cointracker.network

import ca.burchill.cointracker.database.DatabaseCoin
import ca.burchill.cointracker.domain.Coin
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinApiResponse(
    @Json(name = "data")
    val coins: List<NetworkCoin>,
    val status: NetworkStatus
)

@JsonClass(generateAdapter = true)
data class NetworkCoin(
    val circulating_supply: Double,
    @Json(name = "cmc_rank") val cmcRank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val max_supply: Double?,
    val name: String,
    val num_market_pairs: Int,
    val platform: Any?,
    val quote: NetworkQuote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double


) {
    override fun equals(other: Any?) =
        other is NetworkCoin && id == other.id && quote == other.quote

    override fun hashCode(): Int {
        var result = circulating_supply.hashCode()
        result = 31 * result + cmcRank
        result = 31 * result + date_added.hashCode()
        result = 31 * result + id
        result = 31 * result + last_updated.hashCode()
        result = 31 * result + (max_supply?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + num_market_pairs
        result = 31 * result + (platform?.hashCode() ?: 0)
        result = 31 * result + quote.hashCode()
        result = 31 * result + slug.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + total_supply.hashCode()
        return result
    }
}


@JsonClass(generateAdapter = true)
data class NetworkStatus(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: Any?,
    val notice: Any?,
    val timestamp: String,
    val total_count: Int
)

@JsonClass(generateAdapter = true)
data class NetworkQuote(
    val USD: NetworkUSD
)


@JsonClass(generateAdapter = true)
data class NetworkUSD(
    val last_updated: String,
    val market_cap: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_7d: Double,
    val percent_change_90d: Double,
    val price: Double,
    val volume_24h: Double
)


@JsonClass(generateAdapter = true)
data class NetworkCoinContainer(val status: NetworkStatus, val coins: List<NetworkCoin>)


/**
 * Convert Network results to Domain objects
 */
fun NetworkCoinContainer.asDomainModel(): List<Coin> {
    return coins.map {
        Coin(
            circulating_supply = it.circulating_supply,
            cmcRank = it.cmcRank,
            date_added = it.date_added,
            id = it.id,
            max_supply = it.max_supply,
            name = it.name,
            num_market_pairs = it.num_market_pairs,
            slug = it.slug,
            symbol = it.symbol,
            total_supply = it.total_supply,

            market_cap = it.quote.USD.market_cap,
            percent_change_1h = it.quote.USD.percent_change_1h,
            percent_change_24h = it.quote.USD.percent_change_24h,
            percent_change_30d = it.quote.USD.percent_change_30d,
            percent_change_60d = it.quote.USD.percent_change_60d,
            percent_change_7d = it.quote.USD.percent_change_7d,
            percent_change_90d = it.quote.USD.percent_change_90d,
            price = it.quote.USD.price,
            volume_24h = it.quote.USD.volume_24h
        )
    }
}

fun NetworkCoinContainer.asDatabaseModel(): List<DatabaseCoin> {
    return coins.map {
        DatabaseCoin(
            circulating_supply = it.circulating_supply,
            cmcRank = it.cmcRank,
            date_added = it.date_added,
            id = it.id,
            max_supply = it.max_supply,
            name = it.name,
            num_market_pairs = it.num_market_pairs,
            slug = it.slug,
            symbol = it.symbol,
            total_supply = it.total_supply,

            market_cap = it.quote.USD.market_cap,
            percent_change_1h = it.quote.USD.percent_change_1h,
            percent_change_24h = it.quote.USD.percent_change_24h,
            percent_change_30d = it.quote.USD.percent_change_30d,
            percent_change_60d = it.quote.USD.percent_change_60d,
            percent_change_7d = it.quote.USD.percent_change_7d,
            percent_change_90d = it.quote.USD.percent_change_90d,
            price = it.quote.USD.price,
            volume_24h = it.quote.USD.volume_24h
        )
    }
}

