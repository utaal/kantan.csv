/*
 * Copyright 2016 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan

import kantan.codecs._
import kantan.codecs.resource.ResourceIterator

package object csv {
  type CsvReader[A] = ResourceIterator[A]



  // - Backward compatibility ------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** @documentable */
  @deprecated("use CsvSource instead (see https://github.com/nrinaudo/kantan.csv/issues/49)", "0.1.15")
  type CsvInput[A] = CsvSource[A]
  /** @documentable */
  @deprecated("use CsvSource instead (see https://github.com/nrinaudo/kantan.csv/issues/49)", "0.1.15")
  val CsvInput = CsvSource
  /** @documentable */
  @deprecated("use CsvSink instead (see https://github.com/nrinaudo/kantan.csv/issues/49)", "0.1.15")
  type CsvOutput[A] = CsvSink[A]
  /** @documentable */
  @deprecated ("use CsvSink instead (see https://github.com/nrinaudo/kantan.csv/issues/49)", "0.1.15")
  val CsvOutput = CsvSink



  // - Results ---------------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  type Success[A] = Result.Success[A]
  val Success = Result.Success

  type Failure[A] = Result.Failure[A]
  val Failure = Result.Failure

  /** Result of a reading operation, which can be either a success or a failure.
    *
    * Both [[kantan.csv.ParseResult]] and [[DecodeResult]] are valid values of type [[ReadResult]].
    *
    * @see kantan.codecs.Result
    * @documentable
    */
  type ReadResult[A] = Result[ReadError, A]

  /** Result of a parsing operation, which can be either a success or a failure.
    *
    * The difference between a [[ParseResult parse]] and a [[DecodeResult decode]] result is that the former comes
    * from reading raw data and trying to interpret it as CSV, while the later comes from turning CSV data into
    * useful Scala types.
    *
    * Failure cases are all encoded as [[ParseError]].
    *
    * @see kantan.codecs.Result
    * @documentable
    */
  type ParseResult[A] = Result[ParseError, A]

  /** Result of a decode operation, which can be either a success or a failure.
    *
    * The difference between a [[ParseResult parse]] and a [[DecodeResult decode]] result is that the former comes
    * from reading raw data and trying to interpret it as CSV, while the later comes from turning CSV data into
    * useful Scala types.
    *
    * Failure cases are all encoded as [[DecodeError]].
    *
    * @see kantan.codecs.Result
    * @documentable
    */
  type DecodeResult[A] = Result[DecodeError, A]


  // - Cell codecs -----------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** Describes how to decode CSV cells into specific types.
    *
    * All types `A` such that there exists an implicit instance of `CellDecoder[A]` in scope can be decoded from CSV
    * cells.
    *
    * Note that instances of this type class are rarely used directly - their purpose is to be implicitly assembled
    * into more complex instances of [[kantan.csv.RowDecoder]].
    *
    * See the [[CellDecoder$ companion object]] for creation and summoning methods.
    *
    * @tparam A type this instance know to decode from.
    * @see kantan.codecs.Decoder
    * @documentable
    */
  type CellDecoder[A] = Decoder[String, A, DecodeError, codecs.type]

  /** Describes how to encode values of a specific type to CSV cells.
    *
    * All types `A` such that there exists an implicit instance of `CellEncoder[A]` in scope can be encoded to CSV
    * cells.
    *
    * Note that instances of this type class are rarely used directly - their purpose is to be implicitly assembled
    * into more complex instances of [[RowEncoder]].
    *
    * See the [[CellEncoder$ companion object]] for creation and summoning methods.
    *
    * @tparam A type this instance knows to encode to.
    * @see kantan.codecs.Encoder
    * @documentable
    */
  type CellEncoder[A] = Encoder[String, A, codecs.type]

  /** Aggregates a [[CellEncoder]] and a [[CellDecoder]].
    *
    * The sole purpose of this type class is to provide a convenient way to create encoders and decoders. It should
    * not be used directly for anything but instance creation - in particular, it should never be used in a context
    * bound or expected as an implicit parameter.
    *
    * @see kantan.codecs.Codec
    * @documentable
    */
  type CellCodec[A] = Codec[String, A, DecodeError, codecs.type]



  // - Row codecs ------------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** Describes how to decode CSV rows into specific types.
    *
    * All types `A` such that there exists an implicit instance of `RowDecoder[A]` in scope can be decoded from CSV
    * rows by functions such as [[CsvSource.reader]] or [[CsvSource.read]].
    *
    * See the [[RowDecoder$ companion object]] for creation and summoning methods.
    *
    * @tparam A type this instance know to decode from.
    * @see kantan.codecs.Decoder
    * @documentable
    */
  type RowDecoder[A] = Decoder[Seq[String], A, DecodeError, codecs.type]

  /** Describes how to encode values of a specific type to CSV rows.
    *
    * All types `A` such that there exists an implicit instance of `RowEncoder[A]` in scope can be encoded to CSV
    * rows by functions such as [[CsvSink.writer]] or [[CsvSink.write]].
    *
    * See the [[RowEncoder$ companion object]] for creation and summoning methods.
    *
    * @tparam A type this instance knows to encode to.
    * @see kantan.codecs.Encoder
    * @documentable
    */
  type RowEncoder[A] = Encoder[Seq[String], A, codecs.type]

  /** Aggregates a [[RowEncoder]] and a [[RowDecoder]].
    *
    * The sole purpose of this type class is to provide a convenient way to create encoders and decoders. It should
    * not be used directly for anything but instance creation - in particular, it should never be used in a context
    * bound or expected as an implicit parameter.
    *
    * @see kantan.codecs.Codec
    * @documentable
    */
  type RowCodec[A] = Codec[Seq[String], A, DecodeError, codecs.type]
}
