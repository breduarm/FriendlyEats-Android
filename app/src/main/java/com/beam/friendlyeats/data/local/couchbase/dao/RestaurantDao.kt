package com.beam.friendlyeats.data.local.couchbase.dao

import com.beam.friendlyeats.data.local.couchbase.documents.RestaurantDocument
import com.couchbase.lite.Collection
import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Expression
import com.couchbase.lite.Meta
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import kotlinx.coroutines.flow.Flow

interface RestaurantDao {
    fun getById(restaurantId: String): RestaurantDocument?

    fun getByIdFlow(restaurantId: String): Flow<RestaurantDocument?>
}

class RestaurantDaoCouchbaseImpl : RestaurantDao {

    private var database: Database = Database("friendly_eats")
    private var collection: Collection = database.createCollection("restaurants")

    override fun getById(restaurantId: String): RestaurantDocument? {
        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.collection(collection))
            .where(Meta.id.equalTo(Expression.string(restaurantId)))
        val result = query.execute().allResults().firstOrNull()

        return result?.getDictionary(collection.name)?.let {
            RestaurantDocument(
                id = it.getString("id").orEmpty(),
                name = it.getString("name").orEmpty(),
                city = it.getString("city").orEmpty(),
                category = it.getString("category").orEmpty(),
                photo = it.getString("photo").orEmpty(),
                price = it.getInt("price"),
                numRatings = it.getInt("numRatings"),
                avgRating = it.getDouble("avgRating"),
            )
        }
    }

    override fun getByIdFlow(restaurantId: String): Flow<RestaurantDocument?> {
        collection.addDocumentChangeListener(restaurantId) { change ->

        }
        TODO("Not yet implemented")
    }
}
