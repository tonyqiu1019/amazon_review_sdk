# Amazon Review SDK

This developer kit provides an extension of the functionality of our [Amazon Shopping Assistant Chrome extension](https://github.com/Charleo85/IR-experiment-extension/releases). Everyone is welcomed to provide their own implementation of topic model algorithms, for the purpose of matching reviews on Amazon to attributes of the product.

## API Description

Our Chrome extension backend server will call your API using a POST request, with JSON data in the following format:

- `properties`: A list of properties objects, each with the following attributes:
    - `id`: A unique internal identifier of the property, which is required in the returned relationship data;
    - `prod`: The ASIN of the product, corresponding to Amazon;
    - `topic`: The name of the topic for this property;
    - `text_content`: Keywords that might be related to this topic;
    - `xpath`: The xpath of this property on the Amazon product display webpage.
- `reviews`: A list of reviews that need to be matched to properties, each with the following attributes:
    - `content`: The text content of this review;
    - `id`: A unique identifier of the review, corresponding to Amazon;
    - `prod`: Same with the `prod` attribute for `properties`;
    - `page`: The page number of the review on Amazon review display webpage.

For example, below is a possible JSON format:
```
{
  "properties": [
    {
      "id": 1,
      "prod:": "ABCDE01234",
      "topic": "Operating System",
      "text_content": "windows mac os software compatibility",
      "xpath": "//div"
    },
    {
      "id": 5,
      "prod:": "ABCDE01234",
      "topic": "Processor",
      "text_content": "intel amd i3 i5 i7 frequency",
      "xpath": "//other"
    },
  ],
  "reviews": [
    {
      "content": "This computer is great!",
      "id": "ABCDEFG0123456",
      "prod": "ABCDE01234",
      "page": 10
    },
    {
      "content": "This computer is terrible and broken!",
      "id": "ABCDEFG1234567",
      "prod": "ABCDE01234",
      "page": 20
    },
  ]
}
```

Your API should also return JSON data, in the following format:

- `relationships`: A list of relationships picked up by your matching algorithm, each with the following attributes:
    - `best_sentence`: The most critical sentence in the review describing the topic;
    - `related_property_id`: The ID associated with the property, see above;
    - `related_review_id`: The ID associated with the review, see above;
    - `sentiment`: A floating point number from -1 to 1 indicating whether this is a positive(1) or negative(-1) review.

For example, below is a valid return JSON format:
```
{
  "relationships": [
    {
      "best_sentence": "This computer is great!",
      "related_property_id:": 1,
      "related_review_id": "ABCDEFG0123456",
      "sentiment": 0.9
    },
    {
      "best_sentence": "This computer is terrible and broken!",
      "related_property_id:": 5,
      "related_review_id": "ABCDEFG1234567",
      "sentiment": -0.9
    }
  ]
}
```
