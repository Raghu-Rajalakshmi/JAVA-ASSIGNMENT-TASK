# A4 – What Should Be Serialized?

Mark the live JDBC `Connection` transient: it is a process/network resource, not durable application state and is not serializable. Password should not be serialized because it is a secret; preferably store a salted password hash in an authentication system, not a reversible password. Name and register number are ordinary persistent fields; room/hostel relationships are persisted if required. A field marked transient is skipped and has its default value after deserialization (`null` for references).

Serializing the `Hostel` root traverses its reachable serializable object graph: its room list, each Room and each Resident, preserving shared references/cycles using object handles. Every reachable non-transient object must be serializable or serialization fails with `NotSerializableException`.

When adding `phoneNumber`, an old stream can be read if the class keeps a compatible explicit `serialVersionUID`; the new field receives its default value (null) unless migration logic supplies one. An incompatible automatically generated UID can cause `InvalidClassException`. Use explicit UID and validation/migration.

**CSV/JSON alternative:** text formats are larger due to field names/delimiters and encoding but portable, inspectable and language-independent. Saving/loading n residents is O(n) time and O(n) output/input storage (streaming can reduce working memory to O(1) or O(batch)). New optional fields can be added with defaults and schema-version handling; Java serialization is compact/convenient but tightly coupled to Java class evolution and requires compatible classes. JSON is often a better interchange format.
