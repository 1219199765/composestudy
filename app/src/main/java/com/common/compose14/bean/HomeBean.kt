data class HomeBean(
    val `data`: Data?=null,
    val msg: String?=null,
    val status: String?=null
)

{
    data class Data(
        val banner: List<Banner>,
        val grids: List<Grid>,
        val horizontalList: List<Horizontal>,
        val item1: Item1,
        val item2: Item1,
        val item3: Item1,
        val meatLsit: List<Vegetable>,
        val vegetableList: List<Vegetable>
    )

    data class Banner(
        val imageUrl: String
    )

    data class Grid(
        val content: String,
        val detailId: Int,
        val imageUrl: String,
        val layoutId: Int
    )

    data class Horizontal(
        val goodsId: Int,
        val goodsName: String,
        val goodsPrice: Int,
        val imageUrl: String
    )

    data class Item1(
        val detailId: Int,
        val imageUrl: String,
        val layoutId: Int
    )

//    data class MeatLsit(
//        val goodsId: Int,
//        val goodsName: String,
//        val goodsPrice: Int,
//        val imageUrl: String
//    )

    data class Vegetable(
        val goodsId: Int,
        val goodsName: String,
        val goodsPrice: Int,
        val imageUrl: String
    )
}