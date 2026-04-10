# Hotel Management System (HMS)

## Overview

This project is a **Java-based Hotel Management System** developed in two evolutionary phases:

* **Version 1 (V1)** – Focused on core functionality and clean layered architecture
* **Version 2 (V2)** – Focused on concurrency, asynchronous processing, scalability, and production-grade design

The goal of Version 2 was not to add more features, but to **make the system correct, safe, and robust under real-world conditions** such as multiple users, slow external services, and failures.

---

## Version 1 – Functional & Synchronous

### Purpose

Version 1 establishes the **foundation**:

* Clear domain models
* DAO–Service–UI layering
* Simple, understandable flow

### Key Characteristics

* Single-user assumption
* Synchronous (blocking) execution
* Mutable domain objects
* UI directly triggers business operations

### Main Features

* Add rooms and guests
* Create, confirm, check-in, and check-out bookings
* Manually record payments
* Generate invoice synchronously
* Simple reports (revenue by payment mode)

### Typical Flow (V1)

```
UI
 → BookingService
 → PaymentService (manual)
 → InvoiceService
 → UI waits until everything finishes
```

### Limitations

* ❌ Not safe for concurrent users
* ❌ Possible double-booking
* ❌ UI blocks during payment/invoice generation
* ❌ No protection against slow or failing external services

---

## Version 2 – Concurrent, Asynchronous & Production-Ready

### Purpose

Version 2 evolves the system to behave like a **real backend service**:

* Multiple users at the same time
* Slow I/O handled asynchronously
* Failures handled gracefully

### Key Design Goals

* Thread safety
* High concurrency
* Non-blocking workflows
* Failure tolerance
* Clean separation of responsibilities

---

## Major Improvements in Version 2

### 1. Immutable Domain Models

* `Room` objects are immutable
* No setters for critical state like status
* State changes are done by **creating new objects**

**Why?**

* Prevents race conditions
* Makes reasoning about concurrency easier

---

### 2. Concurrency Control (No Double Booking)

* `RoomLockRegistry` provides a `ReentrantLock` per room
* Only the specific room is locked during booking
* Availability is rechecked *inside* the lock

**Result:**

* 100 concurrent users → only 1 booking succeeds

---

### 3. Thread-Safe Data Structures

* DAOs use concurrent collections
* No shared mutable state
* Safe reads and writes under concurrency

---

### 4. Pricing Optimization

* `RatesCatalog` uses `ReadWriteLock`
* Multiple readers allowed concurrently
* Exclusive lock only for rare updates

**Why?**

* Pricing is read-heavy and write-light

---

### 5. Asynchronous Checkout Pipeline

Checkout is now a **non-blocking workflow** built with `CompletableFuture`:

```
Load Booking
 → External Payment Gateway (async)
 → Save Payment Record
 → Generate Invoice (async)
 → Send Email (async)
```

* UI triggers checkout and returns immediately
* Slow I/O runs in a dedicated thread pool

---

### 6. ExecutorService & Thread Pooling

* Dedicated I/O thread pool
* Bounded queue to prevent overload
* Named threads for easier debugging

**Why?**

* Prevents thread explosion
* Improves system stability

---

### 7. Rate Limiting with Semaphore

* External payment gateway protected by `Semaphore`
* Maximum concurrent payment calls enforced

**Why?**

* Prevents overloading fragile external services

---

### 8. Event-Driven Architecture

* Internal `EventBus` using `BlockingQueue`
* Events like `BookingConfirmed`
* Background listeners (e.g., loyalty processing)

**Why?**

* Decouples core booking flow from side effects
* Improves scalability

---

### 9. Timeouts & Compensation (Failure Handling)

* Java-8 compatible timeouts using `ScheduledExecutorService`
* If payment or invoice generation fails:

  * Booking is safely cancelled
  * System does not hang

**Pattern Used:**

* Simplified Saga / Compensation pattern

---

### 10. Concurrency Stress Testing

* Dedicated `ConcurrencyTest`
* 100 threads attempt to book the same room

**Expected Result:**

```
Total bookings created: 1
```

**Why?**

* Correctness is proven, not assumed

---

## UI Differences: Version 1 vs Version 2

### Version 1 UI

* Admin-style
* Manual operations (add room, record payment)
* Synchronous checkout

### Version 2 UI

* User-facing
* Minimal options
* Checkout is **async**
* No manual payment or invoice generation

**Reason:**

> In real systems, UI should trigger workflows, not execute business logic.

---

## Version Comparison Summary

| Aspect           | Version 1 | Version 2 |
| ---------------- | --------- | --------- |
| Concurrency      | ❌                    | ✅         |
| Thread Safety    | ❌                    | ✅         |
| Async Processing | ❌                    | ✅         |
| Rate Limiting    | ❌                    | ✅         |
| Event Driven     | ❌                    | ✅         |
| Failure Handling | ❌                    | ✅         |
| Production Ready | ❌                    | ✅         |

---

## Key Learning Outcomes

* How to evolve a system from *working* to *correct under load*
* Practical Java concurrency patterns
* Asynchronous workflows using `CompletableFuture`
* Designing for failures, not just success

---

## Final Note

Version 2 does not add many new features — instead, it **fixes the hard problems**:

> Concurrency, scalability, and robustness.

This mirrors how real enterprise systems evolve in production.
